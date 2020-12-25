package pl.natusiek.practice.impl.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import pl.natusiek.module.common.extension.sendTitle
import pl.natusiek.practice.api.event.queue.default.JoinQueueEvent
import pl.natusiek.practice.api.event.queue.default.LeaveQueueEvent
import pl.natusiek.practice.api.structure.member.MemberProfile.MemberState
import pl.natusiek.practice.impl.PracticeBootstrapImpl
import pl.natusiek.practice.impl.structure.MemberAPI

class QueueListener(private val bootstrap: PracticeBootstrapImpl): Listener {

    @EventHandler
    fun onJoin(event: JoinQueueEvent) {
        event.players.forEach {
            MemberAPI.assignItem(it, MemberState.QUEUE)
        }
    }

    @EventHandler
    fun onLeave(event: LeaveQueueEvent) {
        event.players.forEach {
            MemberAPI.assignItem(it, MemberState.LOBBY)
            it.sendTitle("", "&cOpuściłeś kolejkę :(", 60)
        }
    }

}