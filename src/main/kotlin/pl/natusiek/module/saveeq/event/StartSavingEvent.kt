package pl.natusiek.module.saveeq.event

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class StartSavingEvent(who: Player, val kit: String) : PlayerEvent(who) {

    override fun getHandlers() = getHandlerList()

    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return this.handlers
        }
    }

}