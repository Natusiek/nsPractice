package pl.natusiek.practice.impl.inventory.queue.ranked

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.colored
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.impl.structure.MatchAPI
import pl.natusiek.practice.impl.structure.QueueAPI

class RankedSelectKitInventoryProvider(private val bootstrap: PracticeBootstrap): InventoryProvider {

    companion object {
        fun getInventory(bootstrap: PracticeBootstrap): SmartInventory =
            SmartInventory.builder()
                .id("RankedSelectKit")
                .provider(RankedSelectKitInventoryProvider(bootstrap))
                .title(" &8* &eWybierz kit!".colored())
                .size(4, 9).build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val member = this.bootstrap.memberRepository.getMemberById(player.uniqueId)!!

        if (member.rankeds.isEmpty()) {
            member.rankeds.addAll(this.bootstrap.kitRepository.kits.filter { it.settings.ranked }.map { Elo(it.name, 1000) })
            getInventory(this.bootstrap).open(player)
        }
        this.bootstrap.kitRepository.kits.filter { it.settings.ranked }.forEach { kit ->
            val elo = member.rankeds.singleOrNull { it.kit == kit.name }
            if (elo == null) {
                member.rankeds.add(Elo(kit.name, 1000))
                getInventory(this.bootstrap).open(player)
            }
            contents.add(ClickableItem.of(kit.icon.toItem(" &8(&f${elo!!.points}&6pkt&8)",
                arrayListOf(
                    "",
                    " &8* &eW grze: &f${MatchAPI.getSizeMatchByKit(kit.name, MatchType.RANKED)}",
                    " &8* &eW kolejce: &f${QueueAPI.getSizeQueueByKit(kit.name, MatchType.RANKED)}",
                    ""
                )
            )) {
                player.closeInventory()
                this.bootstrap.queueRepository.joinToQueue(player, kit.name, MatchType.RANKED, MatchSize.SOLO, MatchRound.BO1)
            })
        }

    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}