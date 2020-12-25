package pl.natusiek.practice.impl.structure.match

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.Match.MatchState.*
import pl.natusiek.practice.api.structure.match.MatchTeam
import pl.natusiek.practice.impl.PracticeAPI

data class MatchImpl(
    override val matchId: Int,
    override val kit: String,
    override val world: String,
    override val teamBlue: MatchTeam,
    override val teamRed: MatchTeam
) : Match {

    override val players: Sequence<Player>
        get() = sequenceOf<Player>()
            .plus(this.teamBlue.players)
            .plus(this.teamRed.players)

    init {
        MatchTask(this)
    }

    override var size: MatchSize = MatchSize.SOLO
    override var round: MatchRound = MatchRound.BO1
    override var type: MatchType = MatchType.UNRANKED
    override val state: MatchState = START

    override var start: Int = 3
    override val time: Long = System.currentTimeMillis()


    class MatchTask(private val match: Match): BukkitRunnable() {

        init {
            this.runTaskTimer(PracticeAPI.plugin, 20, 20)
        }

        override fun run() {
            when (this.match.state) {
                START -> {

                }
                FIGHT -> {

                }
                END -> {

                }
            }
        }

    }
}