package pl.natusiek.practice.impl

import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.repositories.*
import pl.natusiek.practice.impl.command.kit.CreateKitCommand
import pl.natusiek.practice.impl.command.kit.KitCommand
import pl.natusiek.practice.impl.command.kit.RemoveKitCommand
import pl.natusiek.practice.impl.command.kit.ShowKitCommand
import pl.natusiek.practice.impl.listener.MatchListener
import pl.natusiek.practice.impl.listener.PlayerInteractListener
import pl.natusiek.practice.impl.listener.QueueListener
import pl.natusiek.practice.impl.repositories.*
import pl.natusiek.practice.impl.structure.KitAPI
import pl.natusiek.practice.impl.structure.MatchAPI
import pl.natusiek.practice.impl.structure.MemberAPI
import pl.natusiek.practice.impl.structure.QueueAPI
import pl.natusiek.practice.impl.structure.member.MemberProfileImpl
import pl.natusiek.practice.impl.task.HidePlayerTask
import pl.natusiek.practice.impl.task.MatchTask
import pl.natusiek.practice.impl.task.QueueTask

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

        this.registerCommands()
        this.registerListeners()
        this.registerTask()

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


    override fun registerCommands() {
        this.plugin.registerCommands(
            KitCommand(this),
            ShowKitCommand(this),
            CreateKitCommand(this),
            RemoveKitCommand(this)
        )
    }

    override fun registerListeners() {
        this.plugin.registerListeners(
            MatchListener(this),
            QueueListener(this),
            PlayerInteractListener(this)
        )
    }

    override fun registerTask() {
        HidePlayerTask(this)
        MatchTask(this)
        QueueTask(this)
    }

}