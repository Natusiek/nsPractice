package pl.natusiek.practice.impl.inventory.unranked

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.colored
import pl.natusiek.module.common.extension.fillBorder
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.Match.MatchType
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.QueueAPI

class UnrankedSelectKitInventoryProvider(private val bootstrap: PracticeBootstrapImpl) : InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrapImpl): SmartInventory =
            SmartInventory.builder()
                .id("UnrankedSelectKit")
                .provider(UnrankedSelectKitInventoryProvider(bootstrap))
                .title(" &8* &eWybierz kit!".colored())
                .size(4, 9).build()
    }


    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()

        this.bootstrap.kitRepository.kits.forEach {
            contents.add(ClickableItem.of(it.icon.toItem(
                arrayListOf(
                    "",
                    " &8* &eW grze: &f0", // TODO: 25.12.2020 Dodać ile jest w grze!
                    " &8* &eW kolejce: &f${QueueAPI.getSizeQueueByKit(it.name, MatchType.UNRANKED)}",
                    ""
                )
            )) { _ ->
                UnrankedSelectRoundInventoryProvider.getInventory(this.bootstrap, it.name).open(player)
            })
        }

    }

    override fun update(player: Player, contents: InventoryContents) {

    }
}