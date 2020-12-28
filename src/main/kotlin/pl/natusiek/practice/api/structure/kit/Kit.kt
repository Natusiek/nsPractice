package pl.natusiek.practice.api.structure.kit

import org.bukkit.entity.Player
import pl.natusiek.module.common.configuration.structure.ItemStructure

interface Kit {

    val name: String
    val icon: ItemStructure
    val settings: KitSettings
    val equipment: KitEquipment

    fun fillInventoryByKit(player: Player)

}