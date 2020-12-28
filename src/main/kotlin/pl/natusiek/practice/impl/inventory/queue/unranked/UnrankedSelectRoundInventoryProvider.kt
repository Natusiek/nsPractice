package pl.natusiek.practice.impl.inventory.queue.unranked

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

class UnrankedSelectRoundInventoryProvider(private val bootstrap: PracticeBootstrap, val kit: String) : InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrap, kit: String): SmartInventory =
            SmartInventory.builder()
                .id("UnrankedSelectRound")
                .provider(UnrankedSelectRoundInventoryProvider(bootstrap, kit))
                .title(" &8* &eWybierz ile ma być rund!".colored())
                .size(3, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()
        var slot = 2
        MatchRound.values().forEach {
            contents.set(1, slot, ClickableItem.of(HeadBuilder("Natusiek", "&8* &e${it.name} &8*", arrayListOf(
                "",
                " &8* &eW grze: &f0", // TODO: 25.12.2020 Dodać ile jest w grze!
                " &8* &eW kolejce: &f${QueueAPI.getSizeQueueByRound(it, MatchType.UNRANKED)}",
                ""
            )).build()) { _ ->
                player.closeInventory()
                this.bootstrap.queueRepository.searchOrCreateQueue(this.kit, MatchType.UNRANKED, MatchSize.SOLO, it)
            })
            slot += 2
        }

    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}