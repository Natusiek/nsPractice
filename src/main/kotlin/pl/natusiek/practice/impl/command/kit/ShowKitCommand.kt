package pl.natusiek.practice.impl.command.kit

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.practice.api.PracticeBootstrap

@CommandAlias("kit|zestaw")
@CommandPermission("practice.command.kit")
class ShowKitCommand(private val bootstrap: PracticeBootstrap): BaseCommand() {

    @Subcommand("show")
    @Syntax("(name)")
    fun onShow(sender: Player, name: String) {
        val kit = this.bootstrap.kitRepository.getKitByName(name)
            ?: return sender.sendMessages("&4ups! &fNie odnaleziono takiego kitu")

        kit.fillInventoryByKit(sender)
        sender.sendMessages(" &8* &fZostał ci pokazany kit: ${kit.name}")
    }

}