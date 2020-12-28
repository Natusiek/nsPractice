package pl.natusiek.practice.impl.inventory.queue.party

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import pl.natusiek.module.common.builder.HeadBuilder
import pl.natusiek.module.common.extension.colored
import pl.natusiek.module.common.extension.fillBorder
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.impl.structure.QueueAPI

class PartySelectRoundInventoryProvider(private val bootstrap: PracticeBootstrap, val kit: String) : InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrap, kit: String): SmartInventory =
            SmartInventory.builder()
                .id("PartySelectRound")
                .provider(PartySelectRoundInventoryProvider(bootstrap, kit))
                .title(" &8* &eWybierz ile ma być rund!".colored())
                .size(3, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()

        var slot = 2
        MatchRound.values().forEach {
            contents.set(1, slot, ClickableItem.of(HeadBuilder("Natusiek", "&8* &e${it.name} &8*", arrayListOf(
                "",
                " &8* &eW grze: &f${QueueAPI.getSizeQueueByRound(it, MatchType.PARTY)}",
                " &8* &eW kolejce: &f${QueueAPI.getSizeQueueByRound(it, MatchType.PARTY)}",
                ""
            )).build()) { _ ->
                PartySelectSizeInventoryProvider.getInventory(this.bootstrap, this.kit, it)
            })
            slot += 2
        }
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}