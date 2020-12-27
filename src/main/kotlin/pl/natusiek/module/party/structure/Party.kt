package pl.natusiek.module.party.structure

import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import java.io.Serializable
import java.util.*

data class Party(
    val tag: String,
    val leader: UUID
): Serializable {

    val members: MutableSet<PartyMember> = mutableSetOf()
    val invites: MutableMap<UUID, Long> = mutableMapOf()

    val players: Sequence<Player>
        get() = this.members.asSequence().mapNotNull { it.player }

    var state: PartyState = PartyState.LOBBY


    fun isLeader(uniqueId: UUID) = this.leader == uniqueId

    fun hasMember(uniqueId: UUID) = this.members.singleOrNull { it.uniqueId == uniqueId }
    fun hasMember(nick: String) = this.members.singleOrNull { it.name == nick }

    fun sendMessage(vararg message: String) = this.players.forEach { it.sendMessages(message.asList()) }


    enum class PartyState { LOBBY, QUEUE, IN_GAME }

}