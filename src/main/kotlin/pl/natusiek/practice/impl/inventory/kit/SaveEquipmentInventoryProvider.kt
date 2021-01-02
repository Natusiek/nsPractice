package pl.natusiek.practice.impl.inventory.kit

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.colored
import pl.natusiek.module.saveeq.EquipmentAPI
import pl.natusiek.practice.api.PracticeBootstrap

class SaveEquipmentInventoryProvider(private val bootstrap: PracticeBootstrap): InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrap): SmartInventory =
            SmartInventory.builder()
                .id("EquipmentKits")
                .provider(SaveEquipmentInventoryProvider(bootstrap))
                .title(" &8* &eWybierz kit który chcesz edytować!".colored())
                .size(3, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        this.bootstrap.kitRepository.kits.forEach {
            contents.add(ClickableItem.of(it.icon.toItem(
                arrayListOf(
                    "", " &8* &eWybierz kit&8, &ektóry chcesz edytować", ""
                )
            )) { _ ->
                player.closeInventory()
                EquipmentAPI.startSaveEquipment(player, it.name)
            })
        }
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}