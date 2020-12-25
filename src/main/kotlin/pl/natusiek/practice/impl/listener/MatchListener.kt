package pl.natusiek.practice.impl.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import pl.natusiek.practice.api.event.match.default.StartMatchEvent
import pl.natusiek.practice.api.structure.match.MatchTeam
import pl.natusiek.practice.api.structure.match.MatchTeam.*
import pl.natusiek.practice.api.structure.member.MemberProfile
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.KitAPI
import pl.natusiek.practice.impl.structure.MemberAPI

class MatchListener(private val bootstrap: PracticeBootstrapImpl) : Listener {

    @EventHandler
    fun onCreate(event: StartMatchEvent) {
        val match = event.match
        val arena = event.arena

        arena.teleport(match.teamBlue.players.toHashSet(), TeamType.BLUE)
        arena.teleport(match.teamRed.players.toHashSet(), TeamType.RED)

        val kit = KitAPI.findKitByName(match.kit)!!
        match.players.forEach {
            MemberAPI.assignItem(it, MemberState.IN_GAME)
            kit.fillInventoryByKit(it)
        }
    }

}