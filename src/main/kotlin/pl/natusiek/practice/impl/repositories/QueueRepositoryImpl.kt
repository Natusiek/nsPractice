package pl.natusiek.practice.impl.repositories

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendTitle
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.module.party.structure.Party.*
import pl.natusiek.practice.api.PracticeBootstrap

import pl.natusiek.practice.api.event.queue.default.JoinQueueEvent
import pl.natusiek.practice.api.event.queue.default.LeaveQueueEvent
import pl.natusiek.practice.api.repositories.QueueRepository
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.Match.MatchSize.*
import pl.natusiek.practice.api.structure.queue.Queue
import pl.natusiek.practice.api.structure.queue.QueueEntry
import pl.natusiek.practice.impl.structure.MemberAPI
import pl.natusiek.practice.impl.structure.queue.QueueEntryImpl
import pl.natusiek.practice.impl.structure.queue.QueueImpl
import java.util.*

class QueueRepositoryImpl(private val bootstrap: PracticeBootstrap): QueueRepository {

    override val queues: MutableSet<Queue> = mutableSetOf()
    override var number: Int = 0

    override fun joinToQueue(player: Player, kit: String, type: MatchType, size: MatchSize, round: MatchRound) {
        val points: Int? =
            if (type === MatchType.RANKED)
                this.bootstrap.memberRepository.getMemberById(player.uniqueId)!!.rankeds.singleOrNull { it.kit == kit }!!.points
            else null

        val queue = this.searchOrCreateQueue(kit, type, size, round, points)
            ?: return player.sendTitle("", "&cPoczekaj chwilę, jest zbyt dużo kolejek!", 60)

        val entry: QueueEntry =
            when (size) {
                SOLO -> QueueEntryImpl(player.name, player.uniqueId, hashSetOf(player.uniqueId))
                else -> {
                    val party = PartyAPI.findPartyByMemberId(player.uniqueId)
                        ?: return player.sendTitle("", "&cNie posiadasz party!", 60)

                    val members = hashSetOf<UUID>()
                    if (party.players.count() > size.number) {
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

    override fun removeQueue(queue: Queue) { this.queues.remove(queue) }

    override fun getQueueByType(type: MatchType): Sequence<Queue> = this.queues.asSequence().filter { it.type === type }

    override fun getSizeQueueByRound(round: MatchRound, type: MatchType): Int = this.getQueueByType(type).filter { it.round === round }.count()

    override fun getSizeQueueByKit(kit: String, type: MatchType): Int = this.getQueueByType(type).filter { it.kit == it.kit }.count()

    override fun getSizeQueueBySize(size: MatchSize, type: MatchType): Int = this.getQueueByType(type).filter { it.size == size }.count()


    override fun searchOrCreateQueue(kit: String, type: MatchType, size: MatchSize, round: MatchRound, points: Int?): Queue? {
        if (this.queues.size > 20) return null

        val queue = this.queues
            .asSequence()
            .filter { it.kit == kit }
            .filter { it.type === type }
            .filter { it.size === size }
            .filter { it.round === round }
            .filter {
                if (points != null) {
                    it.maxPoints!! > points + 15 && it.minPoints!! < points - 15
                } else {
                    it.maxPoints == null && it.minPoints == null
                }
            }
            .firstOrNull { it.isOpen }
                ?: return QueueImpl(this.number++, kit, type, size, round)
                    .apply {
                        //if (this.type === MatchType.RANKED) {
                        if (points != null) {
                            this.maxPoints = points + 15
                            this.minPoints = points - 15
                        }
                    }.also { this.queues.add(it) }

       return queue
    }

    override fun getQueueBy(block: (Queue) -> Boolean): Queue? = this.queues.find(block)

    override fun getQueueByMemberId(uniqueId: UUID): Queue? = this.getQueueBy { it.getEntryByMember(uniqueId) != null }

}