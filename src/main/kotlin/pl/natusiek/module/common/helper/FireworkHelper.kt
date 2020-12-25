package pl.natusiek.module.common.helper

import org.bukkit.Color
import org.bukkit.FireworkEffect.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.entity.Player

object FireworkHelper {

    private val colors = arrayListOf<Color>(
        Color.AQUA, Color.LIME, Color.PURPLE, Color.WHITE, Color.GREEN, Color.NAVY, Color.FUCHSIA
    )

    fun spawnFirework(player: Player) {
        val firework = player.location.world.spawnEntity(player.location, EntityType.FIREWORK) as Firework
        val fireworkMeta = firework.fireworkMeta

        val effect = builder().withColor(this.colors.random()).with(Type.values().random()).build()

        fireworkMeta.apply {
            this.addEffect(effect)
            this.power = 1
        }
        firework.fireworkMeta = fireworkMeta
    }

}