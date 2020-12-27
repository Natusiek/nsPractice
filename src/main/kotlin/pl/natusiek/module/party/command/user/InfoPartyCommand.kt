package pl.natusiek.module.party.command.user

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule

@CommandAlias("party|team|p|t")
class InfoPartyCommand(private val module: PartyModule): BaseCommand() {

    @Subcommand("info")
    @Syntax("(tag)")
    fun onInfo(sender: Player, tag: String) {
        val party = this.module.partyRepository.getPartyByTag(tag)
            ?: this.module.partyRepository.getPartyByMemberId(sender.uniqueId)
            ?: return sender.sendMessages("&4ups! &fNie odnaleziono party!")

        val leader = party.members.firstOrNull { it.uniqueId == party.leader }!!
        val members = party.members.joinToString { "&e${it.name}" }

        sender.sendMessages(
            "",
            " &8&m----------&r&8(  &e$tag  &8)&m----------",
            "",
            " &7Tag&8: &f$tag",
            " &7Lider&8: &f${leader.name}",
            "",
            " &7Członkowie&8: &e$members",
            "",
            " &8&m----------&r&8(  &e$tag  &8)&m----------",
            ""
        )
    }

}