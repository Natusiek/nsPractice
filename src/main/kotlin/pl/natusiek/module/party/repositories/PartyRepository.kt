package pl.natusiek.module.party.repositories

import pl.natusiek.module.party.PartyModule
import pl.natusiek.module.party.structure.Party
import java.util.*

class PartyRepository(private val module: PartyModule) {

    val parties: MutableSet<Party> = mutableSetOf()


    fun createParty(party: Party) { this.parties.add(party) }

    fun removeParty(party: Party) { this.parties.remove(party) }


    private fun getPartyBy(block: (Party) -> Boolean) = this.parties.find(block)

    fun getPartyByTag(tag: String) = this.getPartyBy { it.tag == tag }

    fun getPartyByMemberId(uniqueId: UUID) = this.getPartyBy { it.hasMember(uniqueId) != null }

}