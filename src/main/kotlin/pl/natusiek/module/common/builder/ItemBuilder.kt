package pl.natusiek.module.common.builder

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import pl.natusiek.module.common.extension.colored

data class ItemBuilder(
   val material: Material,
   val data: Short = 0
) {

    private val item: ItemStack = ItemStack(this.material, 1, this.data)
    private val itemMeta: ItemMeta = this.item.itemMeta

    fun type(material: Material) = apply { this.item.type = material }
    fun amount(amount: Int) = apply { this.item.amount = amount }
    fun durability(durability: Short) = this.item.apply { this.durability = durability }

    fun name(name: String) = apply { this.itemMeta.displayName = name.colored() }
    fun lore(lore: List<String>) = apply { this.itemMeta.lore = lore.colored() }
    fun enchantment(enchants: HashSet<Enchant>) = apply { enchants.forEach { it.addEnchant(this.itemMeta) } }

    fun build(): ItemStack {
        this.item.itemMeta = this.itemMeta
        return this.item
    }


    data class Enchant(val enchant: Int, val level: Int) {

        fun addEnchant(meta: ItemMeta) {
            meta.addEnchant(Enchantment.getById(this.enchant), this@Enchant.level, true)
        }

    }

}