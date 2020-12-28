package pl.natusiek.module.common.configuration.structure

import pl.natusiek.practice.api.structure.kit.KitEquipment
import pl.natusiek.practice.api.structure.kit.KitSettings

data class KitStructure(
    val name: String,
    val icon: ItemStructure,
    val settings: KitSettings,
    val armor: String,
    val contents: String
)