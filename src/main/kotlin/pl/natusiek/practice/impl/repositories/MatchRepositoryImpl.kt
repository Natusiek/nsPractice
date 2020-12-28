package pl.natusiek.practice.impl.repositories

import pl.natusiek.practice.api.PracticeBootstrap

import pl.natusiek.practice.api.repositories.MatchRepository
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.*


class MatchRepositoryImpl(private val bootstrap: PracticeBootstrap) : MatchRepository {

    override val matches: MutableSet<Match> = mutableSetOf()

    override fun createMatch(match: Match) { this.matches.add(match) }

    override fun removeMatch(match: Match) { this.matches.remove(match) }


    override fun getMatchByType(type: MatchType): Sequence<Match> = this.matches.asSequence().filter { it.type == type }

    override fun getSizeMatchBySize(size: MatchSize, type: MatchType): Int = this.getMatchByType(type).filter { it.size === size }.count()

    override fun getSizeMatchByRound(round: MatchRound, type: MatchType): Int = this.getMatchByType(type).filter { it.round === round }.count()

    override fun getSizeMatchByKit(kit: String, type: MatchType): Int = this.getMatchByType(type).filter { it.kit == kit }.count()

}