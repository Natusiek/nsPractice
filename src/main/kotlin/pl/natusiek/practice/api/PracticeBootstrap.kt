package pl.natusiek.practice.api

import pl.natusiek.practice.api.repositories.*

interface PracticeBootstrap {

    val kitRepository: KitRepository
    val matchRepository: MatchRepository
    val arenaRepository: ArenaRepository
    val queueRepository: QueueRepository
    val memberRepository: MemberRepository


    fun onStart()

    fun onStop()

}