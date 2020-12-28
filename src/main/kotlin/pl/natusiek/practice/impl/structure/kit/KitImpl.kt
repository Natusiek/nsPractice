package pl.natusiek.practice.impl.structure.kit

import org.bukkit.entity.Player
import pl.natusiek.module.common.configuration.structure.ItemStructure
import pl.natusiek.practice.api.structure.kit.Kit
import pl.natusiek.practice.api.structure.kit.KitEquipment
import pl.natusiek.practice.api.structure.kit.KitSettings

data class KitImpl(
    override val name: String,
    override val icon: ItemStructure,
    override val settings: KitSettings,
    override val equipment: KitEquipment
) : Kit {

    override fun fillInventoryByKit(player: Player) {
        player.inventory.apply {
            this.armorContents = this@KitImpl.equipment.armor
            this.contents = this@KitImpl.equipment.contents
        }
        player.updateInventory()
    }

}