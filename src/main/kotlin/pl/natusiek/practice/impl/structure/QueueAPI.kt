package pl.natusiek.practice.impl.structure

import pl.natusiek.practice.api.repositories.QueueRepository
import pl.natusiek.practice.api.structure.match.Match.*

object QueueAPI {

    lateinit var queueRepository: QueueRepository

    fun getSizeQueueByRound(round: MatchRound, type: MatchType) = this.queueRepository.getSizeQueueByRound(round, type)

    fun getSizeQueueByKit(kit: String, type: MatchType) = this.queueRepository.getSizeQueueByKit(kit, type)

}