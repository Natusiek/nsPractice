package pl.natusiek.practice.impl.inventory.queue.party

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.colored
import pl.natusiek.module.common.extension.fillBorder
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.impl.structure.MatchAPI
import pl.natusiek.practice.impl.structure.QueueAPI

class PartySelectKitInventoryProvider(private val bootstrap: PracticeBootstrap) : InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrap): SmartInventory =
            SmartInventory.builder()
                .id("PartySelectKit")
                .provider(PartySelectKitInventoryProvider(bootstrap))
                .title(" &8* &eWybierz kit!".colored())
                .size(4, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()
        this.bootstrap.kitRepository.kits.filter { it.settings.ranked }.forEach {
            contents.add(ClickableItem.of(it.icon.toItem(
                arrayListOf(
                    "",
                    " &8* &eW grze: &f${MatchAPI.getSizeMatchByKit(it.name, MatchType.PARTY)}",
                    " &8* &eW kolejce: &f${QueueAPI.getSizeQueueByKit(it.name, MatchType.PARTY)}",
                    ""
                )
            )) { _ ->
                PartySelectRoundInventoryProvider.getInventory(this.bootstrap, it.name).open(player)
            })
        }
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}