package pl.natusiek.module.common.extension

import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketPlayOutChat
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import pl.natusiek.module.common.helper.ItemHelper

fun Player.sendMessages(vararg message: String) {
    message.forEach { this.sendMessage(it.colored()) }
}

fun Player.sendMessages(message: List<String>) {
    message.forEach { this.sendMessage(it.colored()) }
}

fun Player.sendActionBar(text: String) {
    val iChat = IChatBaseComponent.ChatSerializer.a("{\"text\":\"$text\"}".colored())
    val packet = PacketPlayOutChat(iChat, 2.toByte())
    this.sendPacket(packet)
}

fun Player.hasItem(item: ItemStack) = this.inventory.singleOrNull { item.isSimilar(it) } != null

fun Player.sendTitle(title: String, subTitle: String, stay: Int) {
    val timePacket = PacketPlayOutTitle(0, stay, 0)

    val packetTitle = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"$title\"}".colored()))
    val packetSubtitle = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"$subTitle\"}".colored()))

    this.sendPacket(timePacket, packetTitle, packetSubtitle)
}

fun Player.sendPacket(vararg packet: Packet<*>) {
    val playerConnection = (this as CraftPlayer).handle.playerConnection
    packet.forEach { playerConnection.sendPacket(it) }
}