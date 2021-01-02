package pl.natusiek.module.saveeq.repository

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.helper.serializer.InventorySerializer
import pl.natusiek.module.saveeq.SaveEquipmentModule
import pl.natusiek.module.saveeq.event.StartSavingEvent
import pl.natusiek.module.saveeq.structure.Saver
import pl.natusiek.practice.impl.structure.KitAPI
import pl.natusiek.practice.impl.structure.MemberAPI
import java.util.*

class EquipmentRepository(private val module: SaveEquipmentModule) {

    val saver: MutableSet<Saver> = mutableSetOf()

    init {
        EquipmentTask(this.module)
    }

    fun addSaver(saver: Saver) = this.saver.add(saver)

    fun removeSaver(uniqueId: UUID) = this.saver.remove(this.getSaverById(uniqueId)!!)

    fun getSaverById(uniqueId: UUID) = this.saver.singleOrNull { it.uniqueId == uniqueId }

    fun fillInventoryByKit(player: Player, name: String) {
        val member = MemberAPI.findMemberById(player.uniqueId)
        val equipment = member.equipments.singleOrNull { it.name == name }

        if (equipment != null) {
            player.inventory.apply {
                this.armorContents = InventorySerializer.deserializeInventory(equipment.armor)
                this.contents = InventorySerializer.deserializeInventory(equipment.content)
            }
        } else {
            val kit = KitAPI.findKitByName(name)
            kit?.fillInventoryByKit(player)
        }
        player.updateInventory()
    }


    class EquipmentTask(private val module: SaveEquipmentModule) : BukkitRunnable() {

        init {
            this.runTaskTimerAsynchronously(this.module.plugin, 0, 20)
        }

        override fun run() {
            for (saver in this.module.equipmentRepository.saver) {
                if (--saver.coutdown == 0)
                    Bukkit.getPluginManager().callEvent(StartSavingEvent(saver.player, saver.kit))

                saver.sendTitle("", "&eZaraz otrzymasz kit!")
                saver.sendMessage("", " &eUstaw swoj ekwipunek według twoich upodobań i zamknij go, a zostanie zapisany!", "")
            }
        }

    }

}