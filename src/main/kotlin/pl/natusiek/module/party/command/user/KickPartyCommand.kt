package pl.natusiek.module.party.command.user

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule
import pl.natusiek.practice.api.structure.member.MemberProfile
import pl.natusiek.practice.impl.structure.MemberAPI

@CommandAlias("party|team|p|t")
class KickPartyCommand(private val module: PartyModule): BaseCommand() {

    @Subcommand("wyrzuc|kick")
    @Syntax("(nick)")
    fun onKick(sender: Player, nick: String) {
        MemberAPI.findMemberById(sender.uniqueId).also {
            if (it.state != MemberProfile.MemberState.LOBBY)
                return sender.sendMessages("&4ups! &fParty mozesz usunąć tylko w lobby!")
        }
        val party = this.module.partyRepository.getPartyByMemberId(sender.uniqueId)
            ?: return sender.sendMessages("&4ups! &fPosiadasz juz party")

        if (!party.isLeader(sender.uniqueId))
            return sender.sendMessages("&4ups! &fNie jestes liderem!")

        val member = party.hasMember(nick)
            ?: return sender.sendMessages("&4ups! &fNie ma takie gracza w twoim party!")

        if (party.leader == member.uniqueId)
            return sender.sendMessages("&4ups! &fNie możesz wyrzucić lidera!")

        this.module.partyFactory.kickMember(party, member)
    }

}