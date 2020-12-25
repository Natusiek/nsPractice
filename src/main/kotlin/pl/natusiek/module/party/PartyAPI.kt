package pl.natusiek.module.party

import pl.natusiek.module.party.repositories.PartyRepository
import java.util.*

object PartyAPI {

    lateinit var partyRepository: PartyRepository


    fun findPartyByTag(tag: String) = this.partyRepository.getPartyByTag(tag)

    fun findPartyByMemberId(uniqueId: UUID) = this.partyRepository.getPartyByMemberId(uniqueId)

}