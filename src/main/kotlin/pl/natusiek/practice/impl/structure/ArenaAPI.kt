package pl.natusiek.practice.impl.structure

import pl.natusiek.practice.api.repositories.ArenaRepository

object ArenaAPI {

    lateinit var arenaRepository: ArenaRepository

    fun findArenaByName(name: String) = arenaRepository.getArenaByName(name)

    fun getRandomArena() = arenaRepository.getRandomArena()

}