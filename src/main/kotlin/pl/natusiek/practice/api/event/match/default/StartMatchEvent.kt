package pl.natusiek.practice.api.event.match.default

import pl.natusiek.practice.api.event.match.MatchEvent
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.match.Match

open class StartMatchEvent(override val match: Match, val arena: ArenaProfile) : MatchEvent(match)