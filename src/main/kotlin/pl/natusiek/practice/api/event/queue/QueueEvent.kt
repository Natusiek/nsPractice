package pl.natusiek.practice.api.event.queue

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import pl.natusiek.practice.api.structure.queue.Queue

open class QueueEvent(
    open val queue: Queue
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