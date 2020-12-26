package pl.natusiek.practice.impl.repositories

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.event.match.default.EndMatchEvent
import pl.natusiek.practice.api.event.match.default.StartMatchEvent
import pl.natusiek.practice.api.repositories.MatchRepository
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.MatchTeam
import pl.natusiek.practice.impl.PracticeAPI
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.ArenaAPI

class MatchRepositoryImpl(private val bootstrap: PracticeBootstrapImpl) : MatchRepository {

    override val matches: MutableSet<Match> = mutableSetOf()

    init {
        MatchTask(this.bootstrap)
    }

    override fun createMatch(match: Match, arena: ArenaProfile) {
        this.matches.add(match)
        Bukkit.getPluginManager().callEvent(StartMatchEvent(match, arena))
    }

    override fun removeMatch(match: Match) { this.matches.remove(match) }

    class MatchTask(private val bootstrap: PracticeBootstrapImpl): BukkitRunnable() {

        init {
            this.runTaskTimer(this.bootstrap.plugin, 20, 20)
        }

        override fun run() {
            this.bootstrap.matchRepository.matches.forEach { match ->
                when (match.state) {
                    MatchState.PREPARATION -> {
                        Bukkit.getPluginManager()
                            .callEvent(StartMatchEvent(match, ArenaAPI.findArenaByName(match.arena)!!))
                    }
                    MatchState.STARTING -> {
                        if (--match.start > 0)
                            return match.sendTitle("", "&eMecz startuje za: &f${match.start}")

                        match.sendTitle("", "&aGo go go!")
                        match.state = MatchState.FIGHTING
                    }
                    MatchState.FIGHTING -> {
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
                    MatchState.ENDING -> TODO()
                }
            }
        }


    }
}