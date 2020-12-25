package pl.natusiek.practice.api.event.match

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import pl.natusiek.practice.api.structure.match.Match

open class MatchEvent(
    open val match: Match
): Event() {

    override fun getHandlers() = getHandlerList()

    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return this.handlers
        }
    }

}