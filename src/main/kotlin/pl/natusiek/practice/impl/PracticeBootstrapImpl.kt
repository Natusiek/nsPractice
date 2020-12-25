package pl.natusiek.practice.impl

import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.repositories.MemberRepository
import pl.natusiek.practice.api.repositories.QueueRepository
import pl.natusiek.practice.impl.repositories.MemberRepositoryImpl
import pl.natusiek.practice.impl.repositories.QueueRepositoryImpl
import pl.natusiek.practice.impl.structure.MemberAPI

class PracticeBootstrapImpl(private val plugin: PracticePlugin) : PracticeBootstrap {

    override lateinit var queueRepository: QueueRepository
    override lateinit var memberRepository: MemberRepository

    override fun onStart() {
        this.queueRepository = QueueRepositoryImpl(this)
        this.memberRepository = MemberRepositoryImpl(this)

        MemberAPI.memberRepository = this.memberRepository
    }

    override fun onStop() {

    }
}