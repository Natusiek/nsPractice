package pl.natusiek.module.party

import org.bukkit.entity.Player
import pl.natusiek.module.party.factory.PartyFactory
import pl.natusiek.module.party.repositories.PartyRepository
import java.util.*

object PartyAPI {

    lateinit var module: PartyModule


    fun findPartyByTag(tag: String) = this.module.partyRepository.getPartyByTag(tag)

    fun findPartyByMemberId(uniqueId: UUID) = this.module.partyRepository.getPartyByMemberId(uniqueId)

    fun leaveFromParty(player: Player) = this.module.partyFactory.leaveMember(this.findPartyByMemberId(player.uniqueId)!!, player)

}