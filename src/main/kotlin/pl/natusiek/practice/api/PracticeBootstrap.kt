package pl.natusiek.practice.api

import pl.natusiek.practice.api.repositories.KitRepository
import pl.natusiek.practice.api.repositories.MemberRepository
import pl.natusiek.practice.api.repositories.QueueRepository

interface PracticeBootstrap {

    val kitRepository: KitRepository
    val queueRepository: QueueRepository
    val memberRepository: MemberRepository


    fun onStart()

    fun onStop()

}