package pl.natusiek.module.saveeq.event

import org.bukkit.event.HandlerList

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerEvent

class StopSavingEvent(who: Player, val kit: String, val armor: String, val contents: String, val successful: Boolean) : PlayerEvent(who) {

    override fun getHandlers() = getHandlerList()

    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return this.handlers
        }
    }

}