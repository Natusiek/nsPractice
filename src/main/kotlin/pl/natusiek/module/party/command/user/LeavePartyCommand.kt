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
class LeavePartyCommand(private val module: PartyModule): BaseCommand() {

    @Subcommand("opusc|leave")
    @Syntax("(tag)")
    fun onLeave(sender: Player, tag: String) {
        MemberAPI.findMemberById(sender.uniqueId).also {
            if (it.state != MemberProfile.MemberState.LOBBY)
                return sender.sendMessages("&4ups! &fParty mozesz usunąć tylko w lobby!")
        }
        val party = this.module.partyRepository.getPartyByMemberId(sender.uniqueId)
            ?: return sender.sendMessages("&4ups! &fNie posiadasz party!")

        if (party.tag != tag)
            return sender.sendMessages("&4ups! &fPodales zły tag party")

        if (party.isLeader(sender.uniqueId))
            return sender.sendMessages("&4ups! &fJestes liderem więc nie możesz opuścić.")

        this.module.partyFactory.leaveMember(party, sender)
    }

}