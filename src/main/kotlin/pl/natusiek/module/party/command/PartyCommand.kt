package pl.natusiek.module.party.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CatchUnknown
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule

@CommandAlias("party|team|p|t")
class PartyCommand(private val module: PartyModule): BaseCommand() {

    @Default
    @CatchUnknown
    @Subcommand("help")
    fun onHelp(sender: Player) {
        sender.sendMessages(
            "",
            " &8&m----------&r&8(  &ePARTY  &8)&m----------",
            "",
            " &7/party zaloz &8(&etag&8) &0- &fTworzysz party",
            " &7/party usun &8(&etag&8) &0- &Usuwa party",
            " &7/party zapros &8(&enick&8) &0- &fZapraszasz osobe do party",
            " &7/party dolacz &8(&enick&8) &0- &fDolaczasz do party",
            " &7/party wyrzuc &8(&enick&8) &0- &fWyrzuca osobe z party",
            " &7/party opusc &8(&tag&8) &0- &fOdchodzisz z party",
            "",
            " &8&m----------&r&8(  &ePARTY  &8)&m----------",
            ""
        )
    }

}