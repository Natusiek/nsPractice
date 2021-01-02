package pl.natusiek.module.saveeq.listener

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import pl.natusiek.module.common.helper.serializer.InventorySerializer
import pl.natusiek.module.saveeq.SaveEquipmentModule
import pl.natusiek.module.saveeq.event.StopSavingEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerPickupItemEvent

class PlayerListener(private val module: SaveEquipmentModule): Listener {

    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val player = event.player as Player

        val saver = this.module.equipmentRepository.getSaverById(player.uniqueId) ?: return

        val inventory = player.inventory

        val armor = InventorySerializer.serializeInventory(inventory.armorContents)
        val contents = InventorySerializer.serializeInventory(inventory.contents)
        inventory.apply {
            this.clear()
            this.armorContents = arrayOfNulls(4)
        }
        Bukkit.getPluginManager().callEvent(StopSavingEvent(player, saver.kit, armor, contents, true))
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onDamage(event: EntityDamageEvent) {
        if(this.module.equipmentRepository.getSaverById((event.entity as Player).uniqueId) != null) event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onItemPickup(event: PlayerDropItemEvent) {
        if(this.module.equipmentRepository.getSaverById(event.player.uniqueId) != null) event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onItemDrop(event: PlayerPickupItemEvent) {
        if(this.module.equipmentRepository.getSaverById(event.player.uniqueId) != null) event.isCancelled = true
    }
}