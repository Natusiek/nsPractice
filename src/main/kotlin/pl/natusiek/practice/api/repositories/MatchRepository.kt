package pl.natusiek.practice.api.repositories

import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.Match

interface MatchRepository {

    val matches: MutableSet<Match>

    fun createMatch(match: Match, arena: ArenaProfile)

    fun removeMatch(match: Match)

}