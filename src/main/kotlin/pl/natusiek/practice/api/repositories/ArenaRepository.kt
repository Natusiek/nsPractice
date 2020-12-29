package pl.natusiek.practice.api.repositories

import org.bukkit.World
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import java.io.File

interface ArenaRepository {

    val arenas: HashSet<ArenaProfile>
    val arenasBuilds: HashMap<ArenaProfile, World>

    val folder: File

    fun getRandomArena(): ArenaProfile

    fun getArenaByName(name: String): ArenaProfile?

    fun createWorld(arena: ArenaProfile, id: String): World

    fun createBuildWorld(arena: ArenaProfile, id: String)

    fun removeArena(arena: ArenaProfile)

}