package pl.natusiek.practice.api.repositories

import pl.natusiek.practice.api.structure.arena.ArenaProfile
import java.io.File

interface ArenaRepository {

    val arenas: HashSet<ArenaProfile>
    val folder: File

    fun getRandomArena(): ArenaProfile

    fun getArenaByName(name: String): ArenaProfile?

    fun createWorld(arena: ArenaProfile)

}