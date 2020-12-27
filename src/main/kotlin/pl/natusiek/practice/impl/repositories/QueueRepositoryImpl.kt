package pl.natusiek.practice.impl.repositories

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.extension.sendTitle
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.module.party.structure.Party
import pl.natusiek.module.party.structure.Party.*

import pl.natusiek.practice.api.event.queue.default.JoinQueueEvent
import pl.natusiek.practice.api.event.queue.default.LeaveQueueEvent
import pl.natusiek.practice.api.repositories.QueueRepository
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.Match.MatchSize.*
import pl.natusiek.practice.api.structure.queue.Queue
import pl.natusiek.practice.api.structure.queue.QueueEntry
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.queue.QueueEntryImpl
import pl.natusiek.practice.impl.structure.queue.QueueImpl
import java.util.*

class QueueRepositoryImpl(private val bootstrap: PracticeBootstrapImpl): QueueRepository {

    override val queues: MutableSet<Queue> = mutableSetOf()
    override var number: Int = 0

    init {
        QueueTask(this.bootstrap)
    }

    override fun joinToQueue(player: Player, kit: String, type: MatchType, size: MatchSize, round: MatchRound) {
        val queue = this.searchOrCreateQueue(kit, type, size, round)
            ?: return player.sendTitle("", "&cPoczekaj chwilę, jest zbyt dużo kolejek!", 60)

        val entry: QueueEntry =
            when (size) {
                SOLO -> QueueEntryImpl(player.name, player.uniqueId, hashSetOf(player.uniqueId))
                else -> {
                    val party = PartyAPI.findPartyByMemberId(player.uniqueId)
                        ?: return player.sendTitle("", "&cNie posiadasz party!", 60)

                    val members = hashSetOf<UUID>()
                    if (party.members.size > size.number) {
                        val players = party.members.toList()
                        for (i in 0..size.number) {
                            members.add(players[i].uniqueId)
                        }
                    } else {
                        members.addAll(party.members.map { it.uniqueId })
                    }
                    party.state = PartyState.QUEUE
                    QueueEntryImpl(party.tag, player.uniqueId, members)
                }
            }
        queue.entries.add(entry)
        entry.sendTitle("", "&aDołączyłeś do kolejki!")
        Bukkit.getPluginManager().callEvent(JoinQueueEvent(queue, entry.players))
    }

    override fun leaveFromQueue(player: Player) {
        val queue = this.getQueueByMemberId(player.uniqueId)!!
        val entry = queue.getEntryByMember(player.uniqueId)!!

        queue.entries.remove(entry)
        Bukkit.getPluginManager().callEvent(LeaveQueueEvent(queue, entry.players))
    }

    override fun removeQueue(queue: Queue) {
        this.queues.remove(queue)
    }

    override fun getSizeQueueByRound(round: MatchRound, type: MatchType): Int {
        return this.queues.asSequence().filter { it.type === type }.filter { it.round === round }.sumBy { it.entries.size }
    }

    override fun getSizeQueueByKit(kit: String, type: MatchType): Int {
        return this.queues.asSequence().filter { it.type == type }.filter { it.kit == it.kit }.sumBy { it.entries.size }
    }

    override fun searchOrCreateQueue(kit: String, type: MatchType, size: MatchSize, round: MatchRound): Queue? {
        if (this.queues.size > 20) return null

        val queue = this.queues
            .asSequence()
            .filter { it.kit == kit }
            .filter { it.type === type }
            .filter { it.size === size }
            .filter { it.round === round }
            .firstOrNull { it.isOpen }
                ?: return QueueImpl(this.number++, kit, type, size, round).also { this.queues.add(it) }

       return queue
    }

    override fun getQueueBy(block: (Queue) -> Boolean): Queue? = this.queues.find(block)

    override fun getQueueByMemberId(uniqueId: UUID): Queue? = this.getQueueBy { it.getEntryByMember(uniqueId) != null }

    class QueueTask(private val bootstrap: PracticeBootstrapImpl): BukkitRunnable() {

        init {
            this.runTaskTimer(this.bootstrap.plugin,20, 20)
        }

        override fun run() {
            this.bootstrap.queueRepository.queues.forEach {
                if (it.isOpen) {
                    it.start()
                    return this.cancel()
                }
                it.entries.forEach { entry ->
                    entry.sendActionbar("&8|| &fCzekasz juz: ${DataHelper.parseLong(entry.time - System.currentTimeMillis(), false)} &8||")
                    entry.sendTitle("", "&aSzukanie przeciwników...")
                }
            }
        }

    }

}