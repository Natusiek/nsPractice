package pl.natusiek.practice.impl.repositories

import pl.natusiek.module.database.DatabaseAPI
import pl.natusiek.practice.api.repositories.MemberRepository
import pl.natusiek.practice.api.structure.member.MemberProfile
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.member.MemberProfileImpl
import java.util.*

class MemberRepositoryImpl(private val bootstrap: PracticeBootstrapImpl) : MemberRepository {

    override val members: MutableSet<MemberProfile> = mutableSetOf()

    init {
        DatabaseAPI.loadAll<MemberProfileImpl>("members-profile") {
            this.members.addAll(it)
        }
    }

    override fun createMember(member: MemberProfile) {
        if (member is MemberProfileImpl) {
            member.insertEntity()
            this.members.add(member)
        }
    }

    override fun getMemberBy(block: (MemberProfile) -> Boolean): MemberProfile? = this.members.find(block)

    override fun getMemberById(uniqueId: UUID): MemberProfile? = this.getMemberBy { it.uniqueId == uniqueId }

    override fun getMemberByName(name: String): MemberProfile? = this.getMemberBy { it.name == name }

}