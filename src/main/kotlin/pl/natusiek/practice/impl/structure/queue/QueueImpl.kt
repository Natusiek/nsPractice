package pl.natusiek.practice.impl.structure.queue


import pl.natusiek.practice.api.structure.match.Match.*
import pl.natusiek.practice.api.structure.match.MatchTeam.*
import pl.natusiek.practice.api.structure.queue.Queue
import pl.natusiek.practice.api.structure.queue.QueueEntry
import pl.natusiek.practice.impl.structure.ArenaAPI
import pl.natusiek.practice.impl.structure.KitAPI
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

    override val isOpen: Boolean
        get() = this.entries.sumBy { it.members.size } < this.size.number

    override fun start() {
        val entryA = this.entries.first()
        val entryB = this.entries.last()

        val teamA = MatchTeamImpl(entryA.tag, entryA.leader, TeamType.BLUE, entryA.members)
        val teamB = MatchTeamImpl(entryB.tag, entryB.leader, TeamType.RED, entryB.members)
        val arena = ArenaAPI.getRandomArena()
        var arenaName = arena.name
        val kit = KitAPI.findKitByName(this.kit)!!
        if (kit.settings.build) {
            ArenaAPI.arenaRepository.createBuildWorld(arena, this.id.toString())
            arenaName += this.id
        }
        val match = MatchImpl(this.id, this.kit, arenaName, teamA, teamB)
            .apply {
                this.size = this@QueueImpl.size
                this.type = this@QueueImpl.type
                this.round = this@QueueImpl.round
            }
        MatchAPI.createMatch(match, arena)
    }

    override fun getEntryByMember(uniqueId: UUID): QueueEntry? = this.entries.singleOrNull { it.members.firstOrNull { it == uniqueId } != null }

}