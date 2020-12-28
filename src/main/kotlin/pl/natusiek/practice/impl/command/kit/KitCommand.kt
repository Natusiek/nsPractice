package pl.natusiek.practice.impl.command.kit

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.practice.api.PracticeBootstrap

@CommandAlias("kit|zestaw")
@CommandPermission("practice.command.kit")
class KitCommand(private val bootstrap: PracticeBootstrap): BaseCommand() {

    @Default
    @CatchUnknown
    @Subcommand("help")
    fun onHelp(sender: Player) {
        val kits = this.bootstrap.kitRepository.kits.joinToString { "&f${it.name}" }

        sender.sendMessages(
            "",
            " &8&m----------&r&8(  &eKIT  &8)&m----------",
            "",
            " &7/kit utworz &8(&ename&8) &0- &fTworzysz kit",
            " &7/kit usun &8(&ename&8) &0- &fUsuwasz kit",
            " &7/kit pokaz &8(&ename&8) &0- &fZobacz kit",
            " &7/kit ustawienia &8(&ename&8) &0- &fUstawienia kitu",
            "",
            "  &7* &eKity&8: &f$kits",
            "",
            " &8&m----------&r&8(  &eKIT  &8)&m----------",
            ""
        )
    }

}