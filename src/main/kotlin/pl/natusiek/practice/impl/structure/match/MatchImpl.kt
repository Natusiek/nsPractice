package pl.natusiek.practice.impl.structure.match

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.extension.sendActionBar
import pl.natusiek.module.common.extension.sendTitle
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.event.match.default.EndMatchEvent
import pl.natusiek.practice.api.event.match.default.StartMatchEvent
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.Match.MatchState.*
import pl.natusiek.practice.api.structure.match.MatchTeam
import pl.natusiek.practice.api.structure.member.MemberProfile
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.impl.PracticeAPI
import pl.natusiek.practice.impl.structure.ArenaAPI
import pl.natusiek.practice.impl.structure.MemberAPI

data class MatchImpl(
    override val matchId: Int,
    override val kit: String,
    override val arena: String,
    override val teamBlue: MatchTeam,
    override val teamRed: MatchTeam
) : Match {

    override val players: Sequence<Player>
        get() = sequenceOf<Player>()
            .plus(this.teamBlue.players)
            .plus(this.teamRed.players)

    override var size: MatchSize = MatchSize.SOLO
    override var round: MatchRound = MatchRound.BO1
    override var type: MatchType = MatchType.UNRANKED
    override var state: MatchState = STARTING

    override var start: Int = 3
    override val time: Long = System.currentTimeMillis()

    override fun winningRound(team: MatchTeam) {
        this.start = 3
        if (team.leader == this.teamBlue.leader) {
            if (++this.teamBlue.winRate == this.round.number) {
                this.winningMatch(team)
            }
        } else if (++this.teamRed.winRate == this.round.number) {
            this.winningMatch(team)
        }
        this.sendTitle("", "&aRundę wygrał team &f${team.tag}")
        this.state = PREPARATION
    }

    override fun winningMatch(team: MatchTeam) {
        this.sendTitle("&aGratulację!", "&fMecz wygrał team &f${team.tag}")

        this.players
            .map { MemberAPI.findMemberById(it.uniqueId) }
            .forEach { it.state = MemberState.LOBBY }

        Bukkit.getPluginManager().callEvent(EndMatchEvent(this, team))
    }

}