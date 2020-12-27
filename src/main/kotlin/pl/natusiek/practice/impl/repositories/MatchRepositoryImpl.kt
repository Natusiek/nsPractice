package pl.natusiek.practice.impl.repositories

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.PracticeBootstrap
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

class MatchRepositoryImpl(private val bootstrap: PracticeBootstrap) : MatchRepository {

    override val matches: MutableSet<Match> = mutableSetOf()

    override fun createMatch(match: Match, arena: ArenaProfile) {
        this.matches.add(match)
        Bukkit.getPluginManager().callEvent(StartMatchEvent(match, arena))
    }

    override fun removeMatch(match: Match) { this.matches.remove(match) }

}