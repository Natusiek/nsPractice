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
class RemoveKitCommand(private val bootstrap: PracticeBootstrap): BaseCommand() {

    @Subcommand("usun|delete")
    @Syntax("(name)")
    fun onRemove(sender: Player, name: String) {
        val kit = this.bootstrap.kitRepository.getKitByName(name)
            ?: return sender.sendMessages("&4ups! &fNie odnaleziono takiego kitu")

        this.bootstrap.kitRepository.removeKit(kit)
        sender.sendMessages(" &8* &fUsunięto kit: ${kit.name}")
    }

}