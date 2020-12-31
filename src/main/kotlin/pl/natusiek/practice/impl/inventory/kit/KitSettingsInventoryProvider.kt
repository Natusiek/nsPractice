package pl.natusiek.practice.impl.inventory.kit

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.entity.Player
import pl.natusiek.module.common.builder.ItemBuilder
import pl.natusiek.module.common.builder.StatusBuilder
import pl.natusiek.module.common.configuration.ConfigurationService
import pl.natusiek.module.common.configuration.structure.KitStructure
import pl.natusiek.module.common.extension.colored
import pl.natusiek.module.common.extension.fillBorder
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.impl.structure.KitAPI

class KitSettingsInventoryProvider(private val bootstrap: PracticeBootstrap, val kit: String): InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrap, kit: String): SmartInventory =
            SmartInventory.builder()
                .id("KitSettings")
                .provider(KitSettingsInventoryProvider(bootstrap, kit))
                .title(" &8* &eZmień ustawienia kitu!".colored())
                .size(3, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val kit = KitAPI.findKitByName(this.kit) ?: return player.closeInventory()

        val file = this.bootstrap.kitRepository.folder.listFiles().first { it.name == kit.name }
        val structure = ConfigurationService.gson.fromJson(file.readText(), KitStructure::class.java)

        /* 28.12.2020 Jezeli nie dziala to wyzej to porsze użyć tego niżej
        val structure = this.bootstrap.kitRepository.folder.listFiles()
            .map { ConfigurationService.gson.fromJson(it.readText(), KitStructure::class.java) }
            .firstOrNull { it.name == kit.name }
        */

        contents.fillBorder()
        contents.set(1, 2,
            ClickableItem.of(ItemBuilder(Material.CAKE).name("&eParty: ${StatusBuilder(kit.settings.party).status()}").build()) {
                structure.settings.party = !structure.settings.party
                this.refresh(player, structure)
        })
        contents.set(1, 4,
            ClickableItem.of(ItemBuilder(Material.DIAMOND_SWORD).name("&eRankedowe: ${StatusBuilder(kit.settings.ranked).status()}").build()) {
                structure.settings.ranked = !structure.settings.ranked
                this.refresh(player, structure)

        })
        contents.set(1, 6,
            ClickableItem.of(ItemBuilder(Material.LAVA_BUCKET).name("&eBudownicza: ${StatusBuilder(kit.settings.build)}").build()) {
                structure.settings.build = !structure.settings.build
                this.refresh(player, structure)
        })
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

    private fun refresh(player: Player, structure: KitStructure) {
        ConfigurationService.saveInstance(this.bootstrap.kitRepository.folder, structure, structure.name)
        this.bootstrap.kitRepository.refresh()
        getInventory(this.bootstrap, this.kit).open(player)
    }

}