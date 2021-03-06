package pl.natusiek.practice.impl.repositories

import org.apache.commons.io.FileUtils
import org.bukkit.*
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.repositories.ArenaRepository
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import java.io.File

class ArenaRepositoryImpl(private val bootstrap: PracticeBootstrap) : ArenaRepository {

    override val arenas: HashSet<ArenaProfile> = hashSetOf()
    override val arenasBuilds: HashSet<ArenaProfile> = hashSetOf()

    override val folder: File = File(this.bootstrap.plugin.dataFolder, "arenas")

    init {
        for (arena in this.bootstrap.plugin.arenaConfiguration.arenas) {
            this.arenas.add(arena)
        }
    }

    override fun getRandomArena(): ArenaProfile = this.arenas.random()

    override fun getArenaByName(name: String): ArenaProfile? = this.arenas.singleOrNull { it.name == name }


    override fun createWorld(arena: ArenaProfile, id: String) {
        val name = arena.name + id
        FileUtils.copyDirectory(File(this.folder, name), File(this.bootstrap.plugin.server.worldContainer, name))

        WorldCreator(name)
            .environment(World.Environment.NORMAL)
            .generateStructures(false)
            .type(WorldType.FLAT)
            .createWorld().apply {
                this.pvp = true
                this.isAutoSave = false
                this.difficulty = Difficulty.EASY
                this.monsterSpawnLimit = 0
                this.animalSpawnLimit = 0
                setSpawnFlags(false, false)
            }
    }

    override fun createBuildWorld(arena: ArenaProfile, id: String) {
        this.createWorld(arena, id)
        this.arenasBuilds.add(arena)
    }

    override fun removeArena(arena: ArenaProfile, id: String) {
        val world = arena.name + id
        this.bootstrap.plugin.server.unloadWorld(world, false)
        FileUtils.deleteDirectory(File(this.bootstrap.plugin.server.worldContainer, world))
    }

}