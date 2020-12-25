package pl.natusiek.module.party

import pl.natusiek.module.common.plugin.module.Module
import pl.natusiek.module.party.repositories.PartyRepository
import pl.natusiek.practice.impl.PracticePlugin

class PartyModule(private val plugin: PracticePlugin) : Module {

    lateinit var partyRepository: PartyRepository

    override fun onStart() {
        this.partyRepository = PartyRepository(this)

        PartyAPI.partyRepository = this.partyRepository
    }

    override fun onStop() {

    }

}