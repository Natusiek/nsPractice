package pl.natusiek.practice.api.repositories

import org.bukkit.Material
import pl.natusiek.module.common.configuration.structure.KitStructure
import pl.natusiek.practice.api.structure.kit.Kit
import java.io.File

interface KitRepository {

    val kits: MutableSet<Kit>
    val folder: File

    fun createKit(structure: KitStructure)

    fun removeKit(kit: Kit)

    fun refresh()

    fun getKits(block: (kit: Kit) -> Unit)

    fun getKitBy(block: (Kit) -> Boolean): Kit?

    fun getKitByName(name: String): Kit?

    fun getKitByIcon(type: Material): Kit?

}