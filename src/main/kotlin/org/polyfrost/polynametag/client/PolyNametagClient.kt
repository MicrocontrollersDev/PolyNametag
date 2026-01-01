package org.polyfrost.polynametag.client

import dev.deftu.omnicore.api.loader.OmniLoader

object PolyNametagClient {
    fun initialize() {
        PolyNametagConfig.preload()
    }
}
