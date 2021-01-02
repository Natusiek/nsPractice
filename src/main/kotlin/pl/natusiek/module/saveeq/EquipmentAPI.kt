package pl.natusiek.module.saveeq

import org.bukkit.entity.Player
import pl.natusiek.module.saveeq.repository.EquipmentRepository
import pl.natusiek.module.saveeq.structure.Saver

object EquipmentAPI {

    lateinit var equipmentRepository: EquipmentRepository


    fun startSaveEquipment(player: Player, kit: String) = this.equipmentRepository.addSaver(Saver(player.uniqueId, kit))

    fun fillInventoryByKit(player: Player, kit: String) = this.equipmentRepository.fillInventoryByKit(player, kit)

}