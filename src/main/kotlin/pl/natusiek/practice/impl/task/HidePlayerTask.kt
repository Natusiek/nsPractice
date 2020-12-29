package pl.natusiek.practice.impl.task

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.practice.api.PracticeBootstrap

class HidePlayerTask(private val bootstrap: PracticeBootstrap): BukkitRunnable() {

    init {
        this.runTaskTimer(this.bootstrap.plugin, 0, 20)
    }

    override fun run() {
        this.bootstrap.matchRepository.matches.forEach { match ->
            for (player in match.players) {
                Bukkit.getOnlinePlayers()
                    .minus(match.players)
                    .filter { it.world.name == match.arena }
                    .forEach {
                        player.hidePlayer(it)
                    }
            }
        }
    }

}