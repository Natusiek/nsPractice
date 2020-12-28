package pl.natusiek.practice.impl.command.kit

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.natusiek.module.common.configuration.structure.ItemStructure
import pl.natusiek.module.common.configuration.structure.KitStructure
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.common.helper.serializer.InventorySerializer
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.impl.structure.kit.KitEquipmentImpl
import pl.natusiek.practice.impl.structure.kit.KitSettingsImpl

@CommandAlias("kit|zestaw")
@CommandPermission("practice.command.kit")
class CreateKitCommand(private val bootstrap: PracticeBootstrap): BaseCommand() {

    @Subcommand("utworz|create")
    @Syntax("(name)")
    fun onCreate(sender: Player, name: String) {
        if (this.bootstrap.kitRepository.getKitByName(name) != null)
            return sender.sendMessages("&4ups! &fJest juz kit o takiej nazwie")

        val itemInHand = sender.itemInHand
            ?: return sender.sendMessages("&4ups! &fMusisz trzymać coś w dłoni")

        val item = ItemStructure(itemInHand)
        val settings = KitSettingsImpl(party = false, ranked = false, build = false)
        val armor = InventorySerializer.serializeInventory(sender.inventory.armorContents)
        val contents = InventorySerializer.serializeInventory(sender.inventory.contents)

        val structure = KitStructure(name, item, settings, armor, contents)
        this.bootstrap.kitRepository.createKit(structure)
        sender.sendMessages(" &8* &fUtworzono kit: ${structure.name}", " &4PAMIETAJ, &fżeby zmienić ustawienia kitu &e/kit ustawienia &8(&ename&8)")
    }

}