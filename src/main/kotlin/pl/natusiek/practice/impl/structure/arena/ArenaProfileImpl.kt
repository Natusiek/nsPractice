package pl.natusiek.practice.impl.structure.arena

import org.bukkit.entity.Player
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.MatchTeam.TeamType

data class ArenaProfileImpl(
    override val name: String,
    override val world: String,
    override val spawnA: ArenaProfile.ArenaLocation,
    override val spawnB: ArenaProfile.ArenaLocation
) : ArenaProfile {

    override fun teleport(players: HashSet<Player>, team: TeamType) {
        when (team) {
            TeamType.BLUE -> players.forEach { it.teleport(this.spawnA.toLocation(this.world)) }
            TeamType.RED -> players.forEach { it.teleport(this.spawnB.toLocation(this.world)) }
        }
    }

}