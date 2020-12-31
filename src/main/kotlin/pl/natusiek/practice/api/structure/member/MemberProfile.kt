package pl.natusiek.practice.api.structure.member

import java.io.Serializable
import java.util.*
import kotlin.collections.HashSet

interface MemberProfile : Serializable {

    var name: String
    val uniqueId: UUID

    var state: MemberState

    val rankeds: HashSet<Elo>

    enum class MemberState { ANY, LOBBY, QUEUE, IN_GAME }

    data class Elo(val kit: String, val points: Int)

}
