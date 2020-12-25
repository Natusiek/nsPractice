package pl.natusiek.practice.impl.structure

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import pl.natusiek.module.common.builder.ItemBuilder
import pl.natusiek.module.party.PartyAPI
import pl.natusiek.practice.api.repositories.MemberRepository
import pl.natusiek.practice.api.structure.member.MemberProfile.*
import pl.natusiek.practice.api.structure.member.MemberProfile.MemberState.*
import pl.natusiek.practice.impl.PracticeAPI
import java.util.*

object MemberAPI {

    lateinit var memberRepository: MemberRepository

    val items: Array<ItemStack> = arrayOf()

    init {
        this.items.plus(arrayOf(
            ItemBuilder(Material.IRON_SWORD).name("&aDołącz do kolejki &8(Nierankingowe)").build(), // 1
            ItemBuilder(Material.FENCE_GATE).name("&4Opuść kolejkę").build(), // 2
            ItemBuilder(Material.BARRIER).name("&cOpuść party").build() //3
        ))
    }
    fun assignItem(player: Player, state: MemberState) {
        this.findMemberById(player.uniqueId).apply { this.state = state }
        Bukkit.getScheduler().runTaskLater(PracticeAPI.plugin, {
            player.inventory.apply {
                when (state) {
                    LOBBY -> {
                        this.clear()
                        val party = PartyAPI.findPartyByMemberId(player.uniqueId)
                        if (party != null) {
                            if (party.isLeader(player.uniqueId)) {

                            } else {
                                this.setItem(0, this@MemberAPI.items[3])
                            }
                        } else {
                            this.setItem(0, this@MemberAPI.items[1])
                        }
                    }
                    QUEUE -> {
                        this.clear()
                        val party = PartyAPI.findPartyByMemberId(player.uniqueId)
                        if (party != null) {
                            if (party.isLeader(player.uniqueId)) {
                                this.setItem(4, this@MemberAPI.items[2])
                            } else {
                                this.setItem(4, this@MemberAPI.items[3])
                            }
                        } else {
                            this.setItem(4, this@MemberAPI.items[2])
                        }
                    }
                    else -> {

                    }
                }
            }
            player.updateInventory()
        }, 5)
    }

    fun findMemberById(uniqueId: UUID) = this.memberRepository.getMemberById(uniqueId)!!

    fun findMemberByName(name: String) = this.memberRepository.getMemberByName(name)!!

}