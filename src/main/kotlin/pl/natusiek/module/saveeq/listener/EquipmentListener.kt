package pl.natusiek.module.saveeq.listener

import com.avaje.ebean.annotation.EnumValue
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import pl.natusiek.module.common.builder.LocationBuilder
import pl.natusiek.module.common.helper.serializer.InventorySerializer
import pl.natusiek.module.saveeq.SaveEquipmentModule
import pl.natusiek.module.saveeq.event.StartSavingEvent
import pl.natusiek.module.saveeq.event.StopSavingEvent
import pl.natusiek.module.saveeq.structure.Equipment
import pl.natusiek.practice.api.structure.kit.Kit
import pl.natusiek.practice.api.structure.member.MemberProfile.MemberState
import pl.natusiek.practice.impl.structure.KitAPI
import pl.natusiek.practice.impl.structure.MemberAPI

class EquipmentListener(private val module: SaveEquipmentModule): Listener {

    @EventHandler
    fun onStartSaving(event: StartSavingEvent) {
        val player = event.player
        val kit = KitAPI.findKitByName(event.kit)!!

        MemberAPI.assignItem(player, MemberState.SAVING)
        player.teleport(LocationBuilder("world", 500.0, 60.0, 500.0).toBukkitLocation())
        this.giveEquipment(player, kit)
    }

    @EventHandler
    fun onStopSaving(event: StopSavingEvent) {
        val player = event.player
        if (event.successful) {
            val equipments = MemberAPI.findMemberById(player.uniqueId).equipments
            val kit = event.kit
            val equipment = equipments.singleOrNull { it.name == kit }
            if (equipment == null) {
                equipments.add(Equipment(kit, event.armor, event.contents))
            } else {
                equipment.apply {
                    this.armor = event.armor
                    this.content = event.contents
                }
            }
            MemberAPI.assignItem(player, MemberState.LOBBY)
            player.teleport(LocationBuilder("world", 0.0, 65.0, 0.0).toBukkitLocation())
        }
        this.module.equipmentRepository.removeSaver(player.uniqueId)
    }

    fun giveEquipment(player: Player, kit: Kit) {
        player.inventory.apply {
            this.clear()
            this.armorContents = arrayOfNulls(4)

            this.contents = kit.equipment.contents
            this.armorContents = kit.equipment.armor
        }
        player.updateInventory()
    }

}