package pl.natusiek.practice.api.repositories

import org.bukkit.entity.Player
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.queue.Queue
import java.util.*

interface QueueRepository {

    val queues: MutableSet<Queue>
    val number: Int


    fun joinToQueue(player: Player, kit: String, type: MatchType, size: MatchSize, round: MatchRound)

    fun leaveFromQueue(player: Player)

    fun removeQueue(queue: Queue)

    fun getQueueByType(type: MatchType): Sequence<Queue>

    fun getSizeQueueByRound(round: MatchRound, type: MatchType): Int

    fun getSizeQueueByKit(kit: String, type: MatchType): Int

    fun getSizeQueueBySize(size: MatchSize, type: MatchType): Int

    fun searchOrCreateQueue(kit: String, type: MatchType, size: MatchSize, round: MatchRound, points: Int?): Queue?

    fun getQueueBy(block: (Queue) -> Boolean): Queue?

    fun getQueueByMemberId(uniqueId: UUID): Queue?

}