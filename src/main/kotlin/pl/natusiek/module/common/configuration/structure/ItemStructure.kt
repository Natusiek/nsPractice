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
) {

    constructor(item: ItemStack) : this(
        if (item.hasItemMeta() && item.itemMeta.hasDisplayName()) item.itemMeta.displayName else ItemHelper.bukkitName(item.type),
        item.type,
        item.amount,
        0,
    )

    fun toItem(plusname: String, lore: ArrayList<String>): ItemStack = ItemBuilder(this.material, this.data).name(this.name + plusname).amount(this.amount).lore(lore).build()

    fun toItem(lore: ArrayList<String>): ItemStack = this.toItem("", lore)

}