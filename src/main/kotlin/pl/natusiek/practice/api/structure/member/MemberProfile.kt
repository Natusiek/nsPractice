package pl.natusiek.practice.api.structure.member

import pl.natusiek.module.saveeq.structure.Equipment
import java.io.Serializable
import java.util.*
import kotlin.collections.HashSet

interface MemberProfile : Serializable {

    var name: String
    val uniqueId: UUID

    var state: MemberState

    val rankeds: HashSet<Elo>

    val equipments: HashSet<Equipment>


    enum class MemberState { ANY, LOBBY, SAVING, QUEUE, IN_GAME }

    data class Elo(val kit: String, val points: Int)

}
