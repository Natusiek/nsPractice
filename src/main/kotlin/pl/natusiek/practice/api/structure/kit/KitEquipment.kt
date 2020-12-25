package pl.natusiek.practice.api.structure.kit

import org.bukkit.inventory.ItemStack

interface KitEquipment {

    val armor: Array<ItemStack>
    val contents: Array<ItemStack>

}