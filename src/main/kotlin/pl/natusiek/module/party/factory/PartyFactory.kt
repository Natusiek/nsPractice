package pl.natusiek.module.party.factory

import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule
import pl.natusiek.module.party.structure.Party
import pl.natusiek.module.party.structure.PartyMember
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.impl.structure.MemberAPI
import java.util.concurrent.TimeUnit

class PartyFactory(private val module: PartyModule) {

    fun createParty(party: Party, sender: Player) {
        this.module.partyRepository.createParty(party)
        party.members.add(PartyMember(sender.name, sender.uniqueId))
        MemberAPI.assignItem(sender, MemberState.LOBBY)
        sender.sendMessages(" &8* &fUtworzyles party o tagu: &e${party.tag}")
    }

    fun removeParty(party: Party) {
        party.players.forEach {
            it.sendMessages(" &8* &eTwoje party zostało usunięte!")
            MemberAPI.assignItem(it, MemberState.LOBBY)
        }
        this.module.partyRepository.removeParty(party)
    }

    fun inviteMember(party: Party, sender: Player, target: Player) {
        party.invites[target.uniqueId] = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)
        sender.sendMessages(" &8* &fZaprosiłeś gracza &e${target.uniqueId} &fdo party", "   &8* &aPonowanie wpisanie komendy, anuluje zaproszenie")
        target.sendMessages(" &8* &fZostales zaproszony do party &e${party.tag}", "   &8* &aWpisz komende &f/party dolacz ${party.tag}, &aaby dołączyć")
    }

    fun joinMember(party: Party, target: Player) {
        party.members.add(PartyMember(target.name, target.uniqueId))
        party.invites.remove(target.uniqueId)
        party.players.forEach {
            it.sendMessages(" &8* &fDo twojego party dolaczył &e${target.uniqueId}")
        }
        target.sendMessages(" &8* &fDolaczyl do party &e${party.tag}")
    }

    fun leaveMember(party: Party, target: Player) {
        party.members.remove(party.hasMember(target.uniqueId)!!)
        party.players.forEach {
            it.sendMessages(" &8* &e${target.name} &fopuscil twoje party!")
        }
        MemberAPI.assignItem(target, MemberState.LOBBY)
    }

    fun kickMember(party: Party, member: PartyMember) {
        party.members.remove(member)
        party.players.forEach {
            it.sendMessages(" &8* &e${member.name} &fzostal wyrzucony z twojego party!")
        }
    }

}