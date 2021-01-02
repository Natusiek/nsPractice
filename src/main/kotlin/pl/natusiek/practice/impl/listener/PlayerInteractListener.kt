package pl.natusiek.practice.impl.listener


import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.impl.inventory.kit.SaveEquipmentInventoryProvider
import pl.natusiek.practice.impl.inventory.queue.party.PartySelectKitInventoryProvider
import pl.natusiek.practice.impl.inventory.queue.ranked.RankedSelectKitInventoryProvider
import pl.natusiek.practice.impl.inventory.queue.unranked.UnrankedSelectKitInventoryProvider
import pl.natusiek.practice.impl.structure.MemberAPI

class PlayerInteractListener(private val bootstrap: PracticeBootstrap): Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action !== Action.RIGHT_CLICK_AIR) return

        val player = event.player
        val member = MemberAPI.findMemberById(player.uniqueId)
        if (member.state == MemberState.IN_GAME) return

        val itemInHand = player.itemInHand ?: return

        val items = MemberAPI.items.mapNotNull { it.type }
        when (itemInHand.type) {
            items[1] -> UnrankedSelectKitInventoryProvider.getInventory(this.bootstrap).open(player)
            items[2] -> this.bootstrap.queueRepository.leaveFromQueue(player)
            items[3] -> this.bootstrap.queueRepository.leaveFromQueue(player).also { PartyAPI.leaveFromParty(player) }
            items[4] -> PartySelectKitInventoryProvider.getInventory(this.bootstrap).open(player)
            items[5] -> RankedSelectKitInventoryProvider.getInventory(this.bootstrap).open(player)
            items[6] -> SaveEquipmentInventoryProvider.getInventory(this.bootstrap).open(player)
            items[7] -> PartyAPI.openInventory(player)
            else -> {

            }
        }
    }

}