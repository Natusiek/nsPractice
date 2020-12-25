package pl.natusiek.module.common.builder

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

data class HeadBuilder(
    val nick: String = "Natusiek",
    val name: String = "",
    val lore: List<String> = arrayListOf()
) {

    fun build(): ItemStack {
        val skull = ItemBuilder(Material.SKULL_ITEM).name(this.name).lore(this.lore).build()
        val meta = skull.itemMeta as SkullMeta

        meta.owner = this.nick
        skull.itemMeta = meta
        return skull
    }

}