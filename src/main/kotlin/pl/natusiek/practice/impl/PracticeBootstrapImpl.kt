package pl.natusiek.practice.impl

import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.repositories.KitRepository
import pl.natusiek.practice.api.repositories.MemberRepository
import pl.natusiek.practice.api.repositories.QueueRepository
import pl.natusiek.practice.impl.repositories.KitRepositoryImpl
import pl.natusiek.practice.impl.repositories.MemberRepositoryImpl
import pl.natusiek.practice.impl.repositories.QueueRepositoryImpl
import pl.natusiek.practice.impl.structure.KitAPI
import pl.natusiek.practice.impl.structure.MemberAPI
import pl.natusiek.practice.impl.structure.QueueAPI

class PracticeBootstrapImpl(val plugin: PracticePlugin) : PracticeBootstrap {

    override lateinit var kitRepository: KitRepository
    override lateinit var queueRepository: QueueRepository
    override lateinit var memberRepository: MemberRepository

    override fun onStart() {
        this.kitRepository = KitRepositoryImpl(this)
        this.queueRepository = QueueRepositoryImpl(this)
        this.memberRepository = MemberRepositoryImpl(this)

        KitAPI.kitRepository = this.kitRepository
        QueueAPI.queueRepository = this.queueRepository
        MemberAPI.memberRepository = this.memberRepository
    }

    override fun onStop() {

    }
}