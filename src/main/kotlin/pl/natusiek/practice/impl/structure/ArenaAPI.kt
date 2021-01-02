package pl.natusiek.practice.impl.structure

import pl.natusiek.practice.api.repositories.ArenaRepository

object ArenaAPI {

    lateinit var arenaRepository: ArenaRepository

    fun findArenaByName(name: String) = this.arenaRepository.getArenaByName(name)

    fun getRandomArena() = this.arenaRepository.getRandomArena()

}