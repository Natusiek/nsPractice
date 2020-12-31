package pl.natusiek.practice.impl.structure

import org.bukkit.Material
import pl.natusiek.practice.api.repositories.KitRepository

object KitAPI {

    lateinit var kitRepository: KitRepository


    fun findKitByName(name: String) = kitRepository.getKitByName(name)

    fun findKitByIcon(type: Material) = kitRepository.getKitByIcon(type)

}