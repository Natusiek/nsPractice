package pl.natusiek.practice.impl.structure.kit

import pl.natusiek.practice.api.structure.kit.KitSettings

class KitSettingsImpl(
    override var party: Boolean,
    override var ranked: Boolean,
    override var build: Boolean
    ) : KitSettings