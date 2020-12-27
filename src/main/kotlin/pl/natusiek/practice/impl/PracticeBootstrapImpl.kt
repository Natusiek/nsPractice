package pl.natusiek.practice.impl

import pl.natusiek.module.common.configuration.Configuration
import pl.natusiek.module.common.configuration.ConfigurationService
import pl.natusiek.module.common.plugin.Plugin
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.repositories.*
import pl.natusiek.practice.impl.configuration.ArenaConfiguration
import pl.natusiek.practice.impl.repositories.*
import pl.natusiek.practice.impl.structure.KitAPI
import pl.natusiek.practice.impl.structure.MatchAPI
import pl.natusiek.practice.impl.structure.MemberAPI
import pl.natusiek.practice.impl.structure.QueueAPI
import pl.natusiek.practice.impl.structure.member.MemberProfileImpl

class PracticeBootstrapImpl(override val plugin: PracticePlugin): PracticeBootstrap {

    override lateinit var kitRepository: KitRepository
    override lateinit var matchRepository: MatchRepository
    override lateinit var arenaRepository: ArenaRepository
    override lateinit var queueRepository: QueueRepository
    override lateinit var memberRepository: MemberRepository

    override fun onStart() {
        this.kitRepository = KitRepositoryImpl(this)
        this.matchRepository = MatchRepositoryImpl(this)
        this.arenaRepository = ArenaRepositoryImpl(this)
        this.queueRepository = QueueRepositoryImpl(this)
        this.memberRepository = MemberRepositoryImpl(this)

        KitAPI.kitRepository = this.kitRepository
        MatchAPI.matchRepository = this.matchRepository
        QueueAPI.queueRepository = this.queueRepository
        MemberAPI.memberRepository = this.memberRepository
    }

    override fun onStop() {
        this.memberRepository.members.forEach {
            if (it is MemberProfileImpl) it.updateEntity()
        }
    }

}