package pl.natusiek.practice.impl.repositories

import org.bukkit.Bukkit
import pl.natusiek.practice.api.event.match.default.StartMatchEvent
import pl.natusiek.practice.api.repositories.MatchRepository
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.ArenaAPI

class MatchRepositoryImpl(private val bootstrap: PracticeBootstrapImpl) : MatchRepository {

    override val matches: MutableSet<Match> = mutableSetOf()

    override fun createMatch(match: Match, arena: ArenaProfile) {
        this.matches.add(match)
        Bukkit.getPluginManager().callEvent(StartMatchEvent(match, arena))
    }

    override fun removeMatch(match: Match) {

    }

}