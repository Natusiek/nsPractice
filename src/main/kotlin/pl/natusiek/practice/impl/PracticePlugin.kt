package pl.natusiek.practice.impl

import pl.natusiek.module.common.plugin.Plugin
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.impl.PracticeAPI

class PracticePlugin : Plugin() {

    lateinit var bootstrap: PracticeBootstrap

    override fun onEnable() {
        this.bootstrap = PracticeBootstrapImpl(this)

        PracticeAPI.plugin = this
    }

}