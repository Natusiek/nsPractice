package pl.natusiek.practice.api.structure.member

import java.io.Serializable
import java.util.*

interface MemberProfile : Serializable {

    val name: String
    val uniqueId: UUID

    var state: MemberState


    enum class MemberState { ANY, LOBBY, QUEUE, IN_GAME }

}
