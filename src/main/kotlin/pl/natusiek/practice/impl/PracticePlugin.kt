package pl.natusiek.practice.impl

import pl.natusiek.module.common.configuration.ConfigurationService
import pl.natusiek.module.common.plugin.Plugin
import pl.natusiek.module.database.DatabaseModule
import pl.natusiek.module.party.PartyModule
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.impl.PracticeAPI
import pl.natusiek.practice.impl.configuration.ArenaConfiguration

class PracticePlugin : Plugin() {

    lateinit var arenaConfiguration: ArenaConfiguration

    lateinit var partyModule: PartyModule
    lateinit var databaseModule: DatabaseModule

    lateinit var bootstrap: PracticeBootstrap

    override fun onEnable() {
        this.arenaConfiguration = ConfigurationService.load(this.dataFolder, ArenaConfiguration::class)

        this.partyModule = PartyModule(this).also { it.onStart() }
        this.databaseModule = DatabaseModule(this).also { it.onStart() }

        this.bootstrap = PracticeBootstrapImpl(this).also { it.onStart() }

        PracticeAPI.plugin = this
    }

    override fun onDisable() {
        this.bootstrap.onStop()
    }

}