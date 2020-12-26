package pl.natusiek.module.party.command.user

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule

@CommandAlias("party|team|p|t")
class JoinPartyCommand(private val module: PartyModule): BaseCommand() {

    @Subcommand("join|dolacz")
    @Syntax("<tag>")
    fun onJoin(sender: Player, tag: String) {
        if (this.module.partyRepository.getPartyByMemberId(sender.uniqueId) != null)
            return sender.sendMessages("&4ups! &fPosiadasz juz party")

        val party = this.module.partyRepository.getPartyByTag(tag)
            ?: return sender.sendMessages("&4ups! &fNie ma takiego party")

        if (sender.uniqueId !in party.invites)
            return sender.sendMessages("&4ups! &fNie masz zaproszenia do party")

        this.module.partyFactory.joinMember(party, sender)
    }

}