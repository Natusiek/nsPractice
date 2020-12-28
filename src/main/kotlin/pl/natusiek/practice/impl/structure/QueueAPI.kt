package pl.natusiek.practice.impl.structure

import pl.natusiek.practice.api.repositories.QueueRepository
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.queue.Queue

object QueueAPI {

    lateinit var queueRepository: QueueRepository

    fun removeQueue(queue: Queue) = this.queueRepository.removeQueue(queue)

    fun getSizeQueueByRound(round: MatchRound, type: MatchType) = this.queueRepository.getSizeQueueByRound(round, type)

    fun getSizeQueueByKit(kit: String, type: MatchType) = this.queueRepository.getSizeQueueByKit(kit, type)

    fun getSizeQueueBySize(size: MatchSize, type: MatchType) = this.queueRepository.getSizeQueueBySize(size, type)

}