package pl.natusiek.module.party.command.user

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.entity.Player
import pl.natusiek.module.common.extension.sendMessages
import pl.natusiek.module.party.PartyModule
import pl.natusiek.module.party.structure.Party
import pl.natusiek.practice.api.structure.member.MemberProfile.MemberState
import pl.natusiek.practice.impl.structure.MemberAPI

@CommandAlias("party|team|p|t")
class CreatePartyCommand(private val module: PartyModule): BaseCommand() {

    @Subcommand("zaloz|create")
    @Syntax("(tag)")
    fun onCommand(sender: Player, tag: String) {
        MemberAPI.findMemberById(sender.uniqueId).also {
            if (it.state != MemberState.LOBBY)
                return sender.sendMessages("&4ups! &fParty mozesz założyc tylko w lobby!")
        }

        if (this.module.partyRepository.getPartyByMemberId(sender.uniqueId) != null)
            return sender.sendMessages("&4ups! &fPosiadasz juz party!")

        //Tylko małe i duże litery
        val pattern = "[a-zA-Z]+".toRegex()
        if (!tag.matches(pattern))
            return sender.sendMessages("&4ups! &fTag posiada niedozwolone znaki!")

        if (tag.length > 4 || tag.length < 2)
            return sender.sendMessages("&4ups! &fTag ma złe rozmiary")

        if (this.module.partyRepository.getPartyByTag(tag) != null)
            return sender.sendMessages("&4ups! &fParty o takim tagu juz istnieje!")

        this.module.partyFactory.createParty(Party(tag, sender.uniqueId), sender)
    }



}