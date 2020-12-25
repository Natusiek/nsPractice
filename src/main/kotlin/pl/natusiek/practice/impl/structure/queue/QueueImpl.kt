package pl.natusiek.practice.impl.structure.queue

import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.queue.Queue
import pl.natusiek.practice.api.structure.queue.QueueEntry
import pl.natusiek.practice.impl.PracticeAPI

data class QueueImpl(
    override val id: Int,
    override val kit: String,
    override val type: MatchType,
    override val size: MatchSize,
    override val round: MatchRound
): Queue {

    override val entries: MutableSet<QueueEntry> = mutableSetOf()

    init {
        QueueTask(this)
    }

    override val isOpen: Boolean
        get() = this.entries.sumBy { it.members.size } < this.size.number

    override fun start() {

    }


    class QueueTask(private val queue: Queue): BukkitRunnable() {

        init {
            this.runTaskTimer(PracticeAPI.plugin,20, 20)
        }

        override fun run() {
            if (this.queue.isOpen) {
                this.queue.start()
                return this.cancel()
            }
            this.queue.entries.forEach {
                val time = DataHelper.parseLong(it.time - System.currentTimeMillis(), false)
                it.sendActionbar("&8|| &fCzekasz juz: $time &8||")
                it.sendTitle("", "&aSzukanie przeciwników...")
            }
        }

    }

}