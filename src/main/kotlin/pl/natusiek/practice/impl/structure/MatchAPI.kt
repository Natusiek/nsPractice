package pl.natusiek.practice.impl.structure

import pl.natusiek.practice.api.repositories.MatchRepository
import pl.natusiek.practice.api.structure.match.Match

object MatchAPI {

    lateinit var matchRepository: MatchRepository


    fun createMatch(match: Match) = matchRepository.createMatch(match)

    fun getSizeMatchByRound(round: Match.MatchRound, type: Match.MatchType) = matchRepository.getSizeMatchByRound(round, type)

    fun getSizeMatchByKit(kit: String, type: Match.MatchType) = matchRepository.getSizeMatchByKit(kit, type)

    fun getSizeMatchBySize(size: Match.MatchSize, type: Match.MatchType) = matchRepository.getSizeMatchBySize(size, type)

}