package pl.natusiek.module.common.helper

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemHelper {

    fun bukkitName(material: Material): String = material.name.toLowerCase().split("_").map { it.capitalize() }.joinToString { it }.replace(",", "")

}