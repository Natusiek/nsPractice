package pl.natusiek.module.saveeq.structure

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.common.extension.sendTitle
import java.util.*

data class Saver(
    val uniqueId: UUID,
    val kit: String
) {

    val player: Player
        get() = Bukkit.getPlayer(this.uniqueId)

    var given: Boolean = false
    var coutdown: Int = 2

    fun sendMessage(vararg message: String) { this.player.sendMessages(message.toList()) }

    fun sendTitle(title: String, subTitle: String) { this.player.sendTitle(title, subTitle, 60) }

}
