package pl.natusiek.practice.impl.structure.kit

import org.bukkit.inventory.ItemStack
import pl.natusiek.practice.api.structure.kit.KitEquipment

data class KitEquipmentImpl(
    override val armor: Array<ItemStack>,
    override val contents: Array<ItemStack>
    ) : KitEquipment {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KitEquipmentImpl

        if (!armor.contentEquals(other.armor)) return false
        if (!contents.contentEquals(other.contents)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = armor.contentHashCode()
        result = 31 * result + contents.contentHashCode()
        return result
    }

}