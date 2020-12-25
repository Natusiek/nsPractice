package pl.natusiek.module.common.helper

import org.bukkit.Location
import kotlin.math.abs

object LocationHelper {

    fun isInLocation(center: Location, radius: Int, location: Location) = isInLocation(center.blockX, center.blockZ, radius, location.blockX, location.blockZ)

    private fun isInLocation(centerX: Int, centerZ: Int, radius: Int, locX: Int, locZ: Int) =
            abs(centerX - locX) <= radius && abs(centerZ - locZ) <= radius

}