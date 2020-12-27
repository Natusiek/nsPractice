package pl.natusiek.module.party.structure

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

data class PartyMember(
    val name: String,
    val uniqueId: UUID
) {

    val player: Player
        get() = Bukkit.getPlayer(this.uniqueId)


}