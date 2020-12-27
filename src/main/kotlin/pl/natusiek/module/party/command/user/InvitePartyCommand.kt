package pl.natusiek.module.party.command.user

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule

@CommandAlias("party|team|p|t")
class InvitePartyCommand(private val module: PartyModule): BaseCommand() {

    @Subcommand("zapros|invite")
    @Syntax("(nick)")
    @CommandCompletion("@players")
    fun onCommand(sender: Player, @Optional @Flags("other") other: Player?) {
        val party = this.module.partyRepository.getPartyByMemberId(sender.uniqueId)
            ?: return sender.sendMessages("&4ups! &fNie posiadasz party")

        if (!party.isLeader(sender.uniqueId))
            return sender.sendMessages("&4ups! &fNie jestes liderem!")

        val target = other ?: return sender.sendMessages("&4ups! &fNie ma takiego gracza")

        if (party.hasMember(target.uniqueId) != null)
            return sender.sendMessages("&4ups! &fGracz jest u ciebie w party")

        if (this.module.partyRepository.getPartyByMemberId(target.uniqueId) != null)
            return sender.sendMessages("&4ups! &fGracz jest w innym party")

        if (target.uniqueId in party.invites) {
            party.invites.remove(target.uniqueId)
            sender.sendMessages(" &8* &fAnulowałeś zaproszenie gracza &e${target.name} &fdo party")
            return
        }
        this.module.partyFactory.inviteMember(party, sender, target)
    }

}