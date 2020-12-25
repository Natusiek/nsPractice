package pl.natusiek.practice.api.structure.match

import org.bukkit.entity.Player
import java.io.Serializable
import java.util.*
import kotlin.collections.HashSet

interface MatchTeam : Serializable {

    val tag: String
    val leader: UUID
    val team: TeamType
    val members: HashSet<UUID>

    val players: Sequence<Player>

    val winRate: Int

    enum class TeamType { BLUE, RED }

}