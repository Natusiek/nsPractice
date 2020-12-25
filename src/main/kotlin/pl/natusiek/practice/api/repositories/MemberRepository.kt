package pl.natusiek.practice.api.repositories

import pl.natusiek.practice.api.structure.member.MemberProfile
import java.util.*

interface MemberRepository {

    val members: MutableSet<MemberProfile>


    fun createMember(member: MemberProfile)


    fun getMemberBy(block: (MemberProfile) -> Boolean): MemberProfile?

    fun getMemberById(uniqueId: UUID): MemberProfile?

    fun getMemberByName(name: String): MemberProfile?

}