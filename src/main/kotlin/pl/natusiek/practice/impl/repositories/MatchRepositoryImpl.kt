package pl.natusiek.practice.impl.repositories

import org.bukkit.Bukkit
import pl.natusiek.practice.api.PracticeBootstrap

import pl.natusiek.practice.api.repositories.MatchRepository
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.*


class MatchRepositoryImpl(private val bootstrap: PracticeBootstrap) : MatchRepository {

    override val matches: MutableSet<Match> = mutableSetOf()

    override fun createMatch(match: Match) {
        // 30.12.2020 Jak by arena byla (build) zeby zdazyla sie stworzyc i przypalu nie bylo
        Bukkit.getScheduler().runTaskLater(this.bootstrap.plugin, {
            this.matches.add(match)
        }, 5)
    }

    override fun removeMatch(match: Match) { this.matches.remove(match) }


    override fun getMatchByType(type: MatchType): Sequence<Match> = this.matches.asSequence().filter { it.type == type }

    override fun getSizeMatchBySize(size: MatchSize, type: MatchType): Int = this.getMatchByType(type).filter { it.size === size }.count()

    override fun getSizeMatchByRound(round: MatchRound, type: MatchType): Int = this.getMatchByType(type).filter { it.round === round }.count()

    override fun getSizeMatchByKit(kit: String, type: MatchType): Int = this.getMatchByType(type).filter { it.kit == kit }.count()

}