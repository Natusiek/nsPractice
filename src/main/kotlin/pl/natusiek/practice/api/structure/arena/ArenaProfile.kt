package pl.natusiek.practice.api.structure.arena

import org.bukkit.entity.Player
import pl.natusiek.module.common.builder.LocationBuilder
import pl.natusiek.practice.api.structure.match.MatchTeam.*

interface ArenaProfile {

    val name: String
    val world: String
    val spawnA: ArenaLocation
    val spawnB: ArenaLocation

    fun teleport(players: Sequence<Player>, team: TeamType)


    data class ArenaLocation(val x: Double, val y: Double, val z: Double) {

        fun toLocation(world: String) = LocationBuilder(world, this.x, this.y, this.z).toBukkitLocation()

    }

}