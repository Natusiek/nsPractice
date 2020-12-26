package pl.natusiek.practice.api.structure.match

import javafx.scene.media.SubtitleTrack
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendActionBar
import pl.natusiek.module.common.extension.sendTitle
import java.io.Serializable

interface Match : Serializable {

    val matchId: Int
    val kit: String
    val arena: String
    val teamBlue: MatchTeam
    val teamRed: MatchTeam

    val players: Sequence<Player>

    val size: MatchSize
    val round: MatchRound
    val type: MatchType
    var state: MatchState

    var start: Int
    val time: Long


    fun winningRound(team: MatchTeam)
       
    fun winningMatch(team: MatchTeam)


    fun sendActionbar(message: String) = this.players.forEach { it.sendActionBar(message) }

    fun sendTitle(title: String, subTitle: String) = this.players.forEach { it.sendTitle(title, subTitle, 70) }


    enum class MatchState { PREPARATION, STARTING, FIGHTING, ENDING }

    enum class MatchType { RANKED, UNRANKED, PARTY }

    enum class MatchRound(val number: Int) { BO1(1), BO3(2), BO5(3) }

    enum class MatchSize(val number: Int) { SOLO(1), DUO(2), TRIO(3), QUINTET(5) }

}