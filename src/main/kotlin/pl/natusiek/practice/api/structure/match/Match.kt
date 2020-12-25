package pl.natusiek.practice.api.structure.match

import org.bukkit.entity.Player
import java.io.Serializable

interface Match : Serializable {

    val matchId: Int
    val kit: String
    val world: String
    val teamBlue: MatchTeam
    val teamRed: MatchTeam

    val players: Sequence<Player>

    val size: MatchSize
    val round: MatchRound
    val type: MatchType
    val state: MatchState

    val start: Int
    val time: Long


    enum class MatchState { START, FIGHT, END }

    enum class MatchType { RANKED, UNRANKED, PARTY }

    enum class MatchRound(val number: Int) { BO1(1), BO3(2), BO5(3) }

    enum class MatchSize(val number: Int) { SOLO(1), DUO(2), TRIO(3), QUINTET(5) }

}