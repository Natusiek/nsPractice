package pl.natusiek.practice.impl.task

import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.PracticeBootstrap
import java.util.concurrent.TimeUnit

class QueueTask(private val bootstrap: PracticeBootstrap): BukkitRunnable() {

    init {
        this.runTaskTimer(this.bootstrap.plugin,0, 20)
    }

    override fun run() {
        this.bootstrap.queueRepository.queues.forEach {
            if (it.entries.isEmpty()) this.bootstrap.queueRepository.removeQueue(it)
            if (it.isOpen) return it.start()

            if (it.maxPoints != null && it.minPoints != null) {
                it.entries.forEach { entry ->
                    if (entry.time >= System.currentTimeMillis() + TimeUnit.MINUTES.toMinutes(entry.minute.toLong())) {
                        entry.minute += 2
                        it.maxPoints =+ 15
                        it.minPoints =- 15
                        val queue = this.bootstrap.queueRepository.searchOrCreateQueue(it.kit, it.type, it.size, it.round, it.maxPoints!! - (entry.minute * 15))!!
                        if (queue.entries.isEmpty()) {
                            this.bootstrap.queueRepository.removeQueue(queue)
                        } else {
                            // 01.01.2021 Dodalem do dla bezpieczenstwa
                            if (it.isOpen) return it.start()

                            queue.entries.add(entry)
                            this.bootstrap.queueRepository.removeQueue(it)
                        }
                    }
                    entry.sendActionbar("&7| &fKit: &e${it.kit} &8: &fElo: &e${it.minPoints}&7/&e${it.maxPoints} 8: &fCzekasz juz: &e ${DataHelper.parseLong(entry.time - System.currentTimeMillis(), false)} &7|")
                    entry.sendTitle("", "&aSzukanie przeciwników...")
                }
            } else {
                it.entries.forEach { entry ->
                    entry.sendActionbar("&7| &fKit: &e${it.kit} &8: &fCzekasz juz: &e ${DataHelper.parseLong(entry.time - System.currentTimeMillis(), false)} &7|")
                    entry.sendTitle("", "&aSzukanie przeciwników...")
                }
            }
        }
    }

}