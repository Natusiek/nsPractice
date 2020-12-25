package pl.natusiek.practice.impl.configuration

import pl.natusiek.module.common.configuration.Configuration
import pl.natusiek.module.common.configuration.ConfigurationAnnotation
import pl.natusiek.practice.api.structure.arena.ArenaProfile
import pl.natusiek.practice.api.structure.arena.ArenaProfile.ArenaLocation
import pl.natusiek.practice.impl.structure.arena.ArenaProfileImpl

@ConfigurationAnnotation("arenas")
class ArenaConfiguration : Configuration {

    val arenas = hashSetOf(
        ArenaProfileImpl(
            "&aarena1",
            "arena1",
            ArenaLocation(-30.0, 60.0, 30.0),
            ArenaLocation(30.0, 60.0, 30.0)
        )
    )

}