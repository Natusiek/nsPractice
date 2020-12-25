package pl.natusiek.practice.impl.listener


import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.MemberAPI

class PlayerInteractListener(private val bootstrap: PracticeBootstrapImpl): Listener {


    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action !== Action.RIGHT_CLICK_AIR) return

        val player = event.player

        val itemInHand = player.itemInHand ?: return

        val items = MemberAPI.items.mapNotNull { it.type }
        when (itemInHand.type) {
            items[1] -> {

            }
            else -> {

            }
        }
    }

}