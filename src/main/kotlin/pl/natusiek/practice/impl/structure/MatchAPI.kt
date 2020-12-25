package pl.natusiek.practice.impl.structure

import pl.natusiek.practice.api.repositories.MatchRepository
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.Match

object MatchAPI {

    lateinit var matchRepository: MatchRepository


    fun createMatch(match: Match, arena: ArenaProfile) = this.matchRepository.createMatch(match, arena)

}