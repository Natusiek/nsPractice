package pl.natusiek.module.party

import pl.natusiek.module.common.plugin.module.Module
import pl.natusiek.module.party.command.PartyCommand
import pl.natusiek.module.party.command.user.*
import pl.natusiek.module.party.factory.PartyFactory
import pl.natusiek.module.party.listener.PartyChatListener
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

        this.plugin.registerCommands(
            CreatePartyCommand(this),
            InfoPartyCommand(this),
            InvitePartyCommand(this),
            JoinPartyCommand(this),
            KickPartyCommand(this),
            LeavePartyCommand(this),
            RemovePartyCommand(this),
            PartyCommand(this)
        )
        this.plugin.registerListeners(
            PartyChatListener(this)
        )
        PartyAPI.partyRepository = this.partyRepository
    }

    override fun onStop() {

    }

}