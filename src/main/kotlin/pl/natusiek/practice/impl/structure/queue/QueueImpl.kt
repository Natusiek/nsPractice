package pl.natusiek.practice.impl.structure.queue

import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.module.common.helper.DataHelper
import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.MatchTeam
import pl.natusiek.practice.api.structure.match.MatchTeam.*
import pl.natusiek.practice.api.structure.queue.Queue
import pl.natusiek.practice.api.structure.queue.QueueEntry
import pl.natusiek.practice.impl.PracticeAPI
import pl.natusiek.practice.impl.structure.ArenaAPI
import pl.natusiek.practice.impl.structure.MatchAPI
import pl.natusiek.practice.impl.structure.match.MatchImpl
import pl.natusiek.practice.impl.structure.match.MatchTeamImpl
import java.util.*

data class QueueImpl(
    override val id: Int,
    override val kit: String,
    override val type: MatchType,
    override val size: MatchSize,
    override val round: MatchRound
): Queue {

    override val entries: MutableSet<QueueEntry> = mutableSetOf()

    init {
        QueueTask(this)
    }

    override val isOpen: Boolean
        get() = this.entries.sumBy { it.members.size } < this.size.number

    override fun start() {
        val entryA = this.entries.first()
        val entryB = this.entries.last()

        val teamA = MatchTeamImpl(entryA.tag, entryA.leader, TeamType.BLUE, entryA.members)
        val teamB = MatchTeamImpl(entryB.tag, entryB.leader, TeamType.RED, entryB.members)
        val arena = ArenaAPI.getRandomArena()
        val match = MatchImpl(this.id, this.kit, arena.name, teamA, teamB)
            .apply {
                this.size = this@QueueImpl.size
                this.type = this@QueueImpl.type
                this.round = this@QueueImpl.round
            }
        MatchAPI.createMatch(match, arena)
    }

    override fun getEntryByMember(uniqueId: UUID): QueueEntry? = this.entries.singleOrNull { it.members.firstOrNull { it == uniqueId } != null }

    class QueueTask(private val queue: Queue): BukkitRunnable() {

        init {
            this.runTaskTimer(PracticeAPI.plugin,20, 20)
        }

        override fun run() {
            if (this.queue.isOpen) {
                this.queue.start()
                return this.cancel()
            }
            this.queue.entries.forEach {
                val time = DataHelper.parseLong(it.time - System.currentTimeMillis(), false)
                it.sendActionbar("&8|| &fCzekasz juz: $time &8||")
                it.sendTitle("", "&aSzukanie przeciwników...")
            }
        }

    }

}