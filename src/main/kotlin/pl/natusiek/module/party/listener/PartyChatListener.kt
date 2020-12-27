package pl.natusiek.module.party.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule

class PartyChatListener(private val module: PartyModule): Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onAsyncChat(event: AsyncPlayerChatEvent) {
        val sender = event.player

        val party = this.module.partyRepository.getPartyByMemberId(sender.uniqueId) ?: return

        val message = event.message
        if (message.startsWith("@")) {
            party.players.forEach { it.sendMessages("&8[&eParty &7: &f${party.tag}&8] &e${sender.name}&8: &f${message.replace("@", "")}") }
            event.isCancelled = true
        }
    }

}