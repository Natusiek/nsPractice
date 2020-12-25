package pl.natusiek.practice.impl.repositories

import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendTitle
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.module.party.structure.Party
import pl.natusiek.module.party.structure.Party.*
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.repositories.QueueRepository
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.Match.MatchSize.*
import pl.natusiek.practice.api.structure.queue.Queue
import pl.natusiek.practice.api.structure.queue.QueueEntry
import pl.natusiek.practice.impl.structure.queue.QueueEntryImpl
import pl.natusiek.practice.impl.structure.queue.QueueImpl
import java.util.*
import kotlin.collections.HashSet

class QueueRepositoryImpl(private val bootstrap: PracticeBootstrap) : QueueRepository {

    override val queues: MutableSet<Queue> = mutableSetOf()
    override var number: Int = 0

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
                            members.add(players[i])
                        }
                    } else {
                        members.addAll(party.members)
                    }
                    party.state = PartyState.QUEUE
                    QueueEntryImpl(party.tag, player.uniqueId, members)
                }
            }
        entry.sendTitle("", "&aDołączyłeś do kolejki!")
        queue.entries.add(entry)
    }

    override fun leaveFromQueue(player: Player) {

    }

    override fun removeQueue(queue: Queue) {

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
                ?: return QueueImpl(this.number++, kit, type, size, round)

       return queue
    }
}