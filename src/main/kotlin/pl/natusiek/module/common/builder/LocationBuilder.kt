package pl.natusiek.module.common.builder

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

data class LocationBuilder(
    var world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    var yaw: Float,
    var pitch: Float
) {

    constructor(x: Double, y: Double, z: Double, yaw: Float, pitch: Float) : this("world", x, y, z, yaw, pitch)
    constructor(x: Double, y: Double, z: Double) : this("world", x, y, z, 0.0f, 0.0f)
    constructor(world: String, x: Double, y: Double, z: Double) : this(world, x, y, z, 0.0f, 0.0f)

    fun toBukkitLocation(): Location = Location(toBukkitWorld(), this.x, this.y, this.z, this.yaw, this.pitch)

    fun toBukkitWorld(): World = Bukkit.getServer().getWorld(this.world)

}