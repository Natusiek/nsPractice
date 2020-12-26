package pl.natusiek.module.party.task

import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.party.PartyModule

class InviteRefreshTask(private val module: PartyModule): BukkitRunnable() {

    init {
        this.runTaskTimerAsynchronously(this.module.plugin, 0, 360)
    }

    override fun run() {
        this.module.partyRepository.parties.forEach {
            it.invites.forEach { (member, time) ->
                if (time < System.currentTimeMillis()) {
                    it.invites.remove(member)
                }
            }
        }

    }

}