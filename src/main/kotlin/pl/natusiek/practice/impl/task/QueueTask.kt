package pl.natusiek.practice.impl.task

import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.PracticeBootstrap

class QueueTask(private val bootstrap: PracticeBootstrap): BukkitRunnable() {

    init {
        this.runTaskTimer(this.bootstrap.plugin,20, 20)
    }

    override fun run() {
        this.bootstrap.queueRepository.queues.forEach {
            if (it.isOpen)
                return it.start()

            it.entries.forEach { entry ->
                entry.sendActionbar("&8|| &fCzekasz juz: ${DataHelper.parseLong(entry.time - System.currentTimeMillis(), false)} &8||")
                entry.sendTitle("", "&aSzukanie przeciwników...")
            }
        }
    }

}