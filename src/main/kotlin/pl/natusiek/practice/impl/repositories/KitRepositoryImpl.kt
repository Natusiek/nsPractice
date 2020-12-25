package pl.natusiek.practice.impl.repositories

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import pl.natusiek.module.common.configuration.ConfigurationService
import pl.natusiek.module.common.configuration.structure.KitStructure
import pl.natusiek.module.common.helper.serializer.InventorySerializer
import pl.natusiek.practice.api.repositories.KitRepository
import pl.natusiek.practice.api.structure.kit.Kit
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.kit.KitEquipmentImpl
import pl.natusiek.practice.impl.structure.kit.KitImpl
import java.io.File

class KitRepositoryImpl(private val bootstrap: PracticeBootstrapImpl) : KitRepository {

    override val kits: MutableSet<Kit> = mutableSetOf()
    override val folder: File = File(this.bootstrap.plugin.dataFolder, "kits")

    init {
        if (!this.folder.exists()) this.folder.mkdirs()
        this.folder.listFiles()
            .map { ConfigurationService.gson.fromJson(it.readText(), KitStructure::class.java) }
            .map { KitImpl(it.name, it.icon, KitEquipmentImpl(InventorySerializer.deserializeInventory(it.armor), InventorySerializer.deserializeInventory(it.contents))) }
            .forEach { this.kits.add(it) }
    }

    override fun createKit(structure: KitStructure) {
        val kit = KitImpl(
            structure.name,
            structure.icon,
            KitEquipmentImpl(
                InventorySerializer.deserializeInventory(structure.armor),
                InventorySerializer.deserializeInventory(structure.contents)
            )
        )
        this.kits.add(kit)
        ConfigurationService.createJson(File(this.folder, structure.name), ConfigurationService.gson.toJson(structure))
    }

    override fun removeKit(kit: Kit) {
        this.kits.remove(kit)
        ConfigurationService.removeFile(File(this.folder, kit.name))
    }


    override fun getKitBy(block: (Kit) -> Boolean): Kit? = this.kits.find(block)

    override fun getKitByName(name: String): Kit? = this.getKitBy { it.name == name }

    override fun getKitByIcon(type: Material): Kit? = this.getKitBy { it.icon.material === type }

}