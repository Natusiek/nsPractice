package pl.natusiek.practice.impl.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import pl.natusiek.practice.api.PracticeBootstrap
import pl.natusiek.practice.api.structure.member.MemberProfile
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

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player

        val member = this.bootstrap.memberRepository.getMemberById(player.uniqueId) ?: return
        if (member.state === MemberProfile.MemberState.QUEUE) {
            this.bootstrap.queueRepository.leaveFromQueue(player)
        }

    }

}