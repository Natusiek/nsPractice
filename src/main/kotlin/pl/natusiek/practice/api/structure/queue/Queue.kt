package pl.natusiek.practice.api.structure.queue

import pl.natusiek.practice.api.structure.match.Match.*
import java.io.Serializable

interface Queue : Serializable {

    val id: Int
    val kit: String
    val type: MatchType
    val size: MatchSize
    val round: MatchRound

    val entries: MutableSet<QueueEntry>
    val isOpen: Boolean

    fun start()

}