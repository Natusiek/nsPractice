package pl.natusiek.practice.impl.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.impl.structure.member.MemberProfileImpl

class MemberListener(private val bootstrap: PracticeBootstrap): Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.joinMessage = null

        val player = event.player
        val member = this.bootstrap.memberRepository.getMemberById(player.uniqueId)
            ?: return this.bootstrap.memberRepository.createMember(MemberProfileImpl(player.name, player.uniqueId))

        if (member.name != player.name)
            member.name = player.name

    }

}