package pl.natusiek.module.party

import pl.natusiek.module.common.plugin.module.Module
import pl.natusiek.module.party.command.user.InvitePartyCommand
import pl.natusiek.module.party.factory.PartyFactory
import pl.natusiek.module.party.repositories.PartyRepository
import pl.natusiek.module.party.task.InviteRefreshTask
import pl.natusiek.practice.impl.PracticePlugin

class PartyModule(val plugin: PracticePlugin) : Module {

    lateinit var partyFactory: PartyFactory
    lateinit var partyRepository: PartyRepository

    override fun onStart() {
        this.partyFactory = PartyFactory(this)
        this.partyRepository = PartyRepository(this)

        InviteRefreshTask(this)

        PartyAPI.partyRepository = this.partyRepository
    }

    override fun onStop() {

    }

}