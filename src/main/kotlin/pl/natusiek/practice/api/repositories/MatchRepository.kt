package pl.natusiek.practice.api.repositories

import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.*

interface MatchRepository {

    val matches: MutableSet<Match>

    fun createMatch(match: Match)

    fun removeMatch(match: Match)

    fun getMatchByType(type: MatchType): Sequence<Match>
    fun getSizeMatchBySize(size: MatchSize, type: MatchType): Int
    fun getSizeMatchByRound(round: MatchRound, type: MatchType): Int
    fun getSizeMatchByKit(kit: String, type: MatchType): Int

}