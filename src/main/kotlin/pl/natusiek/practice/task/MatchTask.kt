package pl.natusiek.practice.task

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.event.match.default.StartMatchEvent
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.impl.structure.ArenaAPI


class MatchTask(private val bootstrap: PracticeBootstrap): BukkitRunnable() {

    init {
        this.runTaskTimer(this.bootstrap.plugin, 20, 20)
    }

    override fun run() {
        this.bootstrap.matchRepository.matches.forEach { match ->
            when (match.state) {
                Match.MatchState.PREPARATION -> {
                    Bukkit.getPluginManager().callEvent(StartMatchEvent(match, ArenaAPI.findArenaByName(match.arena)!!))
                }
                Match.MatchState.STARTING -> {
                    if (--match.start > 0)
                        return match.sendTitle("", "&eMecz startuje za: &f${match.start}")

                    match.sendTitle("", "&aGo go go!")
                    match.state = Match.MatchState.FIGHTING
                }
                Match.MatchState.FIGHTING -> {
                    match.teamBlue.takeIf { it.players.none { it.gameMode === GameMode.ADVENTURE } }
                        ?.apply { match.winningMatch(match.teamRed) }

                    match.teamRed.takeIf { it.players.none { it.gameMode === GameMode.ADVENTURE } }
                        ?.apply { match.winningRound(match.teamBlue) }

                    match.sendActionbar(
                        "&f${match.teamBlue.tag} &7[&f${match.teamBlue.winRate}&7] &8&l| ${
                            DataHelper.parseLong(System.currentTimeMillis() - match.time, false)
                        } &8&l| &7[&f${match.teamRed.winRate}&7] &f${match.teamRed.winRate}"
                    )
                }
                Match.MatchState.ENDING -> TODO()
            }
        }
    }


}