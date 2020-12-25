package pl.natusiek.practice.api.event.queue.default

import org.bukkit.entity.Player
import pl.natusiek.practice.api.event.queue.QueueEvent
import pl.natusiek.practice.api.structure.queue.Queue

open class LeaveQueueEvent(override val queue: Queue, val players: Sequence<Player>) : QueueEvent(queue)