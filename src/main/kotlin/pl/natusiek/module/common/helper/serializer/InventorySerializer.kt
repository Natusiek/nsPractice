package pl.natusiek.module.common.helper.serializer

import org.bukkit.inventory.ItemStack
import java.io.IOException

import org.bukkit.util.io.BukkitObjectInputStream

import java.io.ByteArrayInputStream

import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayOutputStream
import java.util.*


object InventorySerializer {

    @Throws(IllegalStateException::class)
    fun serializeInventory(items: Array<ItemStack>): String {
        try {
            val outputStream = ByteArrayOutputStream()
            val dataOutput = BukkitObjectOutputStream(outputStream)
            dataOutput.writeObject(items)
            val encoded: String = Base64.getEncoder().encodeToString(outputStream.toByteArray())
            outputStream.close()
            dataOutput.close()
            return encoded
        } catch (e: Exception) {
            throw IllegalStateException("Unable to save itemstack array", e)
        }
    }

    @Throws(IOException::class)
    fun deserializeInventory(data: String): Array<ItemStack> {
        try {
            val inputStream = ByteArrayInputStream(Base64.getDecoder().decode(data))
            val dataInput = BukkitObjectInputStream(inputStream)
            val read: Array<ItemStack> = dataInput.readObject() as Array<ItemStack>
            inputStream.close()
            dataInput.close()
            return read
        } catch (e: ClassNotFoundException) {
            throw IOException("Unable to read class type", e)
        }
    }

}