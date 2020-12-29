package pl.natusiek.practice.impl.structure

import org.bukkit.Bukkit
import pl.natusiek.practice.api.repositories.MatchRepository
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.Match

object MatchAPI {

    lateinit var matchRepository: MatchRepository


    fun createMatch(match: Match) = this.matchRepository.createMatch(match)

    fun getSizeMatchByRound(round: Match.MatchRound, type: Match.MatchType) = this.matchRepository.getSizeMatchByRound(round, type)

    fun getSizeMatchByKit(kit: String, type: Match.MatchType) = this.matchRepository.getSizeMatchByKit(kit, type)

    fun getSizeMatchBySize(size: Match.MatchSize, type: Match.MatchType) = this.matchRepository.getSizeMatchBySize(size, type)

}