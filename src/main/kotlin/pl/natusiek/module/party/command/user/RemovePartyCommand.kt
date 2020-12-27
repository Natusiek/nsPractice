package pl.natusiek.module.party.command.user

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import com.google.common.cache.CacheBuilder
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule
import pl.natusiek.practice.api.structure.member.MemberProfile
import pl.natusiek.practice.impl.structure.MemberAPI
import java.util.*
import java.util.concurrent.TimeUnit

@CommandAlias("party|team|p|t")
class RemovePartyCommand(private val module: PartyModule): BaseCommand() {

    private val cooldowns = CacheBuilder.newBuilder()
        .maximumSize(10000)
        .expireAfterWrite(3, TimeUnit.MINUTES)
        .build<UUID, Long>()

    @Subcommand("usun|remove|delete")
    @Syntax("(tag)")
    fun onRemove(sender: Player, tag: String) {
        MemberAPI.findMemberById(sender.uniqueId).also {
            if (it.state != MemberProfile.MemberState.LOBBY)
                return sender.sendMessages("&4ups! &fParty mozesz usunąć tylko w lobby!")
        }
        val party = this.module.partyRepository.getPartyByMemberId(sender.uniqueId)
            ?: return sender.sendMessages("&4ups! &fNie posiadasz party!")

        if (party.tag != tag)
            return sender.sendMessages("&4ups! &fPodales zly tag party!")

        if (!party.isLeader(sender.uniqueId))
            return sender.sendMessages("&4ups! &fNie jestes liderem!")

        sender.sendMessages(" &8* &fAby usunąc party wpisz: &e/party potwierdz")
        this.cooldowns.put(sender.uniqueId, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3))
    }

    @Subcommand("potwierdz|accept")
    fun onAccept(sender: Player) {
        MemberAPI.findMemberById(sender.uniqueId).also {
            if (it.state != MemberProfile.MemberState.LOBBY)
                return sender.sendMessages("&4ups! &fParty mozesz usunąć tylko w lobby!")
        }

        val cooldown = this.cooldowns.getIfPresent(sender.uniqueId)
            ?: return sender.sendMessages("&4ups! &fNie jestes w trakcie usuwania party!")

        if (cooldown >= System.currentTimeMillis()) {
            val party = this.module.partyRepository.getPartyByMemberId(sender.uniqueId) ?: return

            //dla pewnosci!
            if (party.isLeader(sender.uniqueId))
                return sender.sendMessages("&4ups! &fNie jestes liderem!")

            this.cooldowns.asMap().remove(sender.uniqueId)
            sender.sendMessages(" &cSzkoda! &fUsunąłeś party o tagu: &e${party.tag}")
            this.module.partyFactory.removeParty(party)
        }
    }

}