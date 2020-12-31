package pl.natusiek.practice.impl.listener

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import pl.natusiek.module.common.builder.LocationBuilder
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.event.match.default.EndMatchEvent
import pl.natusiek.practice.api.event.match.default.StartMatchEvent
import pl.natusiek.practice.api.structure.match.Match.MatchState
import pl.natusiek.practice.api.structure.match.MatchTeam.*
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.impl.structure.ArenaAPI
import pl.natusiek.practice.impl.structure.KitAPI
import pl.natusiek.practice.impl.structure.MemberAPI

class MatchListener(private val bootstrap: PracticeBootstrap) : Listener {

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
        match.state = MatchState.STARTING
    }

    @EventHandler
    fun onEnd(event: EndMatchEvent) {
        val match = event.match
        val team = event.team
        val spawn = LocationBuilder("world", 0.0, 65.0, 0.0).toBukkitLocation()
        match.players.forEach {
            it.sendMessages(
                "",
                " &8* &fWygrał: &a${team.tag}",
                "   &* &fOsoby: &a${team.players.joinToString { "&a${it.name}" }}",
                ""
            )
            it.teleport(spawn)
            it.gameMode = GameMode.ADVENTURE
        }
        val kit = KitAPI.findKitByName(match.kit)!!
        if (kit.settings.build) {
            val arena = ArenaAPI.findArenaByName(match.arena)!!
            this.bootstrap.arenaRepository.removeArena(arena, match.matchId.toString())
        }
        this.bootstrap.matchRepository.removeMatch(match)
    }

}