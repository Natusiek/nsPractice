package pl.natusiek.practice.impl.structure.match

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.natusiek.practice.api.structure.match.MatchTeam
import pl.natusiek.practice.api.structure.match.MatchTeam.*
import java.util.*
import kotlin.collections.HashSet

data class MatchTeamImpl(
    override val tag: String,
    override val leader: UUID,
    override val team: TeamType,
    override val members: HashSet<UUID>
    ) : MatchTeam {

    override val players: Sequence<Player>
        get() = this.members.asSequence().mapNotNull { Bukkit.getPlayer(it) }

    override var winRate: Int = 0

}