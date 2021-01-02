package pl.natusiek.module.saveeq

import pl.natusiek.module.common.plugin.module.Module
import pl.natusiek.module.saveeq.listener.EquipmentListener
import pl.natusiek.module.saveeq.listener.PlayerListener
import pl.natusiek.module.saveeq.repository.EquipmentRepository
import pl.natusiek.practice.impl.PracticePlugin

class SaveEquipmentModule(val plugin: PracticePlugin): Module {

    lateinit var equipmentRepository: EquipmentRepository

    override fun onStart() {
        this.equipmentRepository = EquipmentRepository(this)
        this.plugin.registerListeners(
            PlayerListener(this),
            EquipmentListener(this)
        )
        EquipmentAPI.equipmentRepository = this.equipmentRepository
    }

    override fun onStop() {

    }

}