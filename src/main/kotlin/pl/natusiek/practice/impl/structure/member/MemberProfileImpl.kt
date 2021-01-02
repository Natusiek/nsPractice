package pl.natusiek.practice.impl.structure.member

import pl.natusiek.module.database.system.DatabaseEntity
import pl.natusiek.module.saveeq.structure.Equipment
import pl.natusiek.practice.api.structure.member.MemberProfile
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import java.util.*
import kotlin.collections.HashSet

data class MemberProfileImpl(
    override var name: String,
    override val uniqueId: UUID
) : MemberProfile, DatabaseEntity() {

    override var state: MemberState = MemberState.LOBBY

    override val rankeds: HashSet<Elo> = hashSetOf()
    override val equipments: HashSet<Equipment> = hashSetOf()

    override val id: String get() = this.uniqueId.toString()
    override val key: String get() = "uuid"
    override val collection: String get() = "members-profile"

}