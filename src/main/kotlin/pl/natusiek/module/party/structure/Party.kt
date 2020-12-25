package pl.natusiek.module.party.structure

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import java.util.*

data class Party(
    val tag: String,
    val leader: UUID
) {

    val members: MutableSet<UUID> = mutableSetOf()

    val players: Sequence<Player>
        get() = this.members.asSequence().mapNotNull { Bukkit.getPlayer(it) }

    var state: PartyState = PartyState.LOBBY


    fun isLeader(uniqueId: UUID) = this.leader == uniqueId

    fun hasMember(uniqueId: UUID) = this.members.singleOrNull { it == uniqueId }

    fun sendMessage(vararg message: String) = this.players.forEach { it.sendMessages(message.asList()) }

    enum class PartyState { LOBBY, QUEUE, IN_GAME }

}