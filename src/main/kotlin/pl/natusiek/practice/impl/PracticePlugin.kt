package pl.natusiek.practice.impl

import pl.natusiek.module.common.configuration.ConfigurationService
import pl.natusiek.module.common.plugin.Plugin
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.impl.PracticeAPI
import pl.natusiek.practice.impl.configuration.ArenaConfiguration

class PracticePlugin : Plugin() {

    lateinit var arenaConfiguration: ArenaConfiguration

    lateinit var bootstrap: PracticeBootstrap

    override fun onEnable() {
        this.arenaConfiguration = ConfigurationService.load(this.dataFolder, ArenaConfiguration::class)

        this.bootstrap = PracticeBootstrapImpl(this)

        this.bootstrap.onStart()

        PracticeAPI.plugin = this
    }

    override fun onDisable() {
        this.bootstrap.onStop()
    }

}