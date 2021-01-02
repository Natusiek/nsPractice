package pl.natusiek.module.party.inventory

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta
import pl.natusiek.module.common.builder.HeadBuilder
import pl.natusiek.module.common.builder.ItemBuilder
import pl.natusiek.module.common.extension.colored
import pl.natusiek.module.common.extension.fill
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.module.party.PartyModule
import pl.natusiek.module.party.inventory.page.MemberPartyInventoryProvider

class MenuPartyInventoryProvider(private val module: PartyModule): InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PartyModule): SmartInventory =
            SmartInventory.builder()
                .id("MenuParty")
                .provider(MenuPartyInventoryProvider(bootstrap))
                .title(" &8* &eMenu twojego party.".colored())
                .size(5, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fill()

        val party = PartyAPI.findPartyByMemberId(player.uniqueId) ?: return

        contents.set(4, 2, ClickableItem.empty(ItemBuilder(Material.SIGN).name(" &8* &e${party.tag} &8*").build()))
        contents.set(2, 3, ClickableItem.of(HeadBuilder(party.members.random().name, " &8* &eCzłonkowie &8*").build()) {
            MemberPartyInventoryProvider.getInventory(this.module).open(player)
        })
        contents.set(6, 3, ClickableItem.of(ItemBuilder(Material.RECORD_11).name(" &8* &eUstawienia &8*").build()) {

        })
    }

    override fun update(player: Player, contents: InventoryContents) {
        val party = PartyAPI.findPartyByMemberId(player.uniqueId) ?: return

        val item = contents.get(2, 3).get()
        val meta = item.item.itemMeta as SkullMeta

        meta.owner = party.members.random().name
        item.item.itemMeta = meta
        contents.set(2, 3, item)
    }

}