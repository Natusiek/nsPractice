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
import pl.natusiek.practice.impl.structure.MatchAPI
import pl.natusiek.practice.impl.structure.QueueAPI

class PartySelectSizeInventoryProvider(private val bootstrap: PracticeBootstrap, val kit: String, val round: MatchRound): InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrap, kit: String, round: MatchRound): SmartInventory =
            SmartInventory.builder()
                .id("PartySelectRound")
                .provider(PartySelectSizeInventoryProvider(bootstrap, kit, round))
                .title(" &8* &eWybierz ile ma byc graczy!".colored())
                .size(3, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()
        var slot = 1
        MatchSize.values().forEach {
            contents.set(1, slot, ClickableItem.of(HeadBuilder("Natusiek", "&8* &e${it.name} &7(&f${it.number}&7) &8*", arrayListOf(
                "",
                " &8* &eW grze: &f${MatchAPI.getSizeMatchBySize(it, MatchType.PARTY)}",
                " &8* &eW kolejce: &f${QueueAPI.getSizeQueueBySize(it, MatchType.PARTY)}",
                "",
            )).build()) { _ ->
                player.closeInventory()
                this.bootstrap.queueRepository.joinToQueue(player, this.kit, MatchType.PARTY, it, this.round)
            })
            slot += 2
        }
    }

    override fun update(player: Player, contents: InventoryContents) {
        TODO("Not yet implemented")
    }
}