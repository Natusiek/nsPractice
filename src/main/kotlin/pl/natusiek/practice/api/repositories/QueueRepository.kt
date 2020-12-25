package pl.natusiek.practice.api.repositories

import org.bukkit.entity.Player
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.queue.Queue

interface QueueRepository {

    val queues: MutableSet<Queue>
    val number: Int

    fun joinToQueue(player: Player, kit: String, type: MatchType, size: MatchSize, round: MatchRound)

    fun leaveFromQueue(player: Player)

    fun removeQueue(queue: Queue)

    fun searchOrCreateQueue(kit: String, type: MatchType, size: MatchSize, round: MatchRound): Queue?

}