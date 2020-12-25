package pl.natusiek.practice.impl.structure

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import pl.natusiek.module.common.builder.ItemBuilder
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.practice.api.repositories.MemberRepository
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.api.structure.member.MemberProfile.MemberState.*
import java.util.*

object MemberAPI {

    lateinit var memberRepository: MemberRepository

    val items: Array<ItemStack> = arrayOf()

    init {
        this.items.plus(arrayOf(
            ItemBuilder(Material.IRON_SWORD).name("&aDołącz do kolejki &8(Nierankingowe)").build(), // 1
        ))
    }
    fun assignItem(player: Player, state: MemberState) {
        this.findMemberById(player.uniqueId).apply { this.state = state }
        player.inventory.apply {
            this.clear()
            when (state) {
                ANY -> {}
                LOBBY -> {
                    val party = PartyAPI.findPartyByMemberId(player.uniqueId)
                    if (party != null) {
                        if (party.isLeader(player.uniqueId)) {

                        } else {

                        }
                    } else {
                        this.setItem(0, this@MemberAPI.items[1])
                    }
                }
                QUEUE -> {

                }
                IN_GAME -> {

                }
            }
        }
    }

    fun findMemberById(uniqueId: UUID) = this.memberRepository.getMemberById(uniqueId)!!

    fun findMemberByName(name: String) = this.memberRepository.getMemberByName(name)!!

}