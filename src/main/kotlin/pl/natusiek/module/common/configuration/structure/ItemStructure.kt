package pl.natusiek.module.common.configuration.structure

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import pl.natusiek.module.common.builder.ItemBuilder
import pl.natusiek.module.common.helper.ItemHelper

class ItemStructure(
    val name: String,
    val material: Material,
    val amount: Int,
    val data: Short,
    val lore: List<String>
) {

    constructor(item: ItemStack) : this(
        if (item.hasItemMeta() && item.itemMeta.hasDisplayName()) item.itemMeta.displayName else ItemHelper.bukkitName(item.type),
        item.type,
        item.amount,
        0,
        if (item.hasItemMeta() && item.itemMeta.hasLore()) item.itemMeta.lore else arrayListOf()
    )

    fun toItem(name: String): ItemStack = ItemBuilder(this.material, this.data).name(name).amount(this.amount).lore(this.lore).build()

}