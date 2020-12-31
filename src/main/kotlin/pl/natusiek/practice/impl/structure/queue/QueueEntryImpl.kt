package pl.natusiek.practice.impl.structure.queue

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendActionBar
import pl.natusiek.module.common.extension.sendTitle
import pl.natusiek.practice.api.structure.queue.QueueEntry
import java.util.*
import kotlin.collections.HashSet

data class QueueEntryImpl(
    override val tag: String,
    override val leader: UUID,
    override val members: HashSet<UUID>
    ) : QueueEntry {

    override val players: Sequence<Player>
        get() = this.members
            .asSequence()
            .mapNotNull { Bukkit.getPlayer(it) }

    override var minute: Int = 2
    override val time: Long = System.currentTimeMillis()


    override fun sendActionbar(message: String) { this.players.forEach { it.sendActionBar(message) } }

    override fun sendTitle(title: String, subTitle: String) { this.players.forEach { it.sendTitle(title, subTitle, 80) } }

}