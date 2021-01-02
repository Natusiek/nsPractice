package pl.natusiek.module.party.inventory.page

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import pl.natusiek.module.common.builder.HeadBuilder
import pl.natusiek.module.common.extension.colored
import pl.natusiek.module.common.extension.fillBorder
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.module.party.PartyModule

class MemberPartyInventoryProvider(private val module: PartyModule): InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PartyModule): SmartInventory =
            SmartInventory.builder()
                .id("MenuParty")
                .provider(MemberPartyInventoryProvider(bootstrap))
                .title(" &8* &eCzłonkowie twojego party.".colored())
                .size(5, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()

        val party = PartyAPI.findPartyByMemberId(player.uniqueId) ?: return
        for (member in party.members) {
            contents.add(ClickableItem.of(HeadBuilder(member.name, " &8* &e${member.name} &8*").build()) {
                if (party.leader == player.uniqueId) {

                }
            })
        }
    }

    override fun update(player: Player, contents: InventoryContents) {
    }

}