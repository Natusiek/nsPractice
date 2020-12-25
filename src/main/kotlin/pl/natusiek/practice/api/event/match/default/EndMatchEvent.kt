package pl.natusiek.practice.api.event.match.default

import pl.natusiek.practice.api.event.match.MatchEvent
import pl.natusiek.practice.api.structure.match.Match
import pl.natusiek.practice.api.structure.match.MatchTeam

open class EndMatchEvent(override val match: Match, val team: MatchTeam) : MatchEvent(match)