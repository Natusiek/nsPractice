package pl.natusiek.practice.api.structure.queue

import org.bukkit.entity.Player
import java.io.Serializable
import java.util.*
import kotlin.collections.HashSet

interface QueueEntry : Serializable {

    val tag: String
    val leader: UUID
    val members: HashSet<UUID>

    var minute: Int
    val time: Long
    val players: Sequence<Player>


    fun sendActionbar(message: String)
    fun sendTitle(title: String, subTitle: String)

}