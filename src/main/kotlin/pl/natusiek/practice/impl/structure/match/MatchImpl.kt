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

    init {
        MatchTask(this)
    }

    override var size: MatchSize = MatchSize.SOLO
    override var round: MatchRound = MatchRound.BO1
    override var type: MatchType = MatchType.UNRANKED
    override var state: MatchState = STARTING

    override var start: Int = 3
    override val time: Long = System.currentTimeMillis()

    class MatchTask(private val match: Match): BukkitRunnable() {
        // TODO: 25.12.2020 Będzie trzeba zmienić że wszystkie w (for) a nie każdy mecz oddzielnie
        init {
            this.runTaskTimer(PracticeAPI.plugin, 20, 20)
        }

        override fun run() {
            when (this.match.state) {
                PREPARATION -> {
                    Bukkit.getPluginManager().callEvent(StartMatchEvent(this.match, ArenaAPI.findArenaByName(this.match.arena)!!))
                }
                STARTING -> {
                    if (--this.match.start > 0)
                        return this.match.sendTitle("", "&eMecz startuje za: &f${this.match.start}")

                    this.match.sendTitle("", "&aGo go go!")
                    this.match.state = FIGHTING
                }
                FIGHTING -> {
                    this.match.teamBlue.takeIf { it.players.none { it.gameMode === GameMode.ADVENTURE } }
                        ?.apply { winningMatch(this@MatchTask.match.teamRed) }

                    this.match.teamRed.takeIf { it.players.none { it.gameMode === GameMode.ADVENTURE } }
                        ?.apply { winningRound(this@MatchTask.match.teamBlue) }

                    this.match.sendActionbar(
                        "&f${this.match.teamBlue.tag} &7[&f${this.match.teamBlue.winRate}&7] &8&l| ${DataHelper.parseLong(System.currentTimeMillis() - this.match.time, false)} &8&l| &7[&f${this.match.teamRed.winRate}&7] &f${this.match.teamRed.winRate}"
                    )
                }
                ENDING -> TODO()
            }
        }

        fun winningRound(team: MatchTeam) {
            this.match.start = 3
            if (team.leader == this.match.teamBlue.leader) {
                if (++this.match.teamBlue.winRate == this.match.round.number) {
                    this.winningMatch(team)
                }
            } else if (++this.match.teamRed.winRate == this.match.round.number) {
                this.winningMatch(team)
            }
            this.match.sendTitle("", "&aRundę wygrał team &f${team.tag}")
            this.match.state = PREPARATION
        }

        fun winningMatch(team: MatchTeam) {
            this.match.sendTitle("&aGratulację!", "&fMecz wygrał team &f${team.tag}")

            this.match.players
                .map { MemberAPI.findMemberById(it.uniqueId) }
                .forEach { it.state = MemberState.LOBBY }

            Bukkit.getPluginManager().callEvent(EndMatchEvent(this.match, team))
            this.cancel()
        }

    }
}