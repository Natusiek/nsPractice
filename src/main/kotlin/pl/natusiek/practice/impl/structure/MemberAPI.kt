package pl.natusiek.practice.impl.structure

import pl.natusiek.practice.api.repositories.MemberRepository
import java.util.*

object MemberAPI {

    lateinit var memberRepository: MemberRepository


    fun findMemberById(uniqueId: UUID) = this.memberRepository.getMemberById(uniqueId)

    fun findMemberByName(name: String) = this.memberRepository.getMemberByName(name)

}