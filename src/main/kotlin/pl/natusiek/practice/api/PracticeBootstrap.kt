package pl.natusiek.practice.api

import pl.natusiek.practice.api.repositories.*
import pl.natusiek.practice.impl.PracticePlugin

interface PracticeBootstrap {

    val plugin: PracticePlugin

    val kitRepository: KitRepository
    val matchRepository: MatchRepository
    val arenaRepository: ArenaRepository
    val queueRepository: QueueRepository
    val memberRepository: MemberRepository


    fun onStart()

    fun onStop()


    fun registerCommands()

    fun registerListeners()

}