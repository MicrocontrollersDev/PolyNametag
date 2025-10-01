package org.polyfrost.polynametag.client

import dev.deftu.omnicore.api.loader.OmniLoader

object PolyNametagClient {
    @JvmStatic
    var isEssential = false
        private set

    var isPatcher = false
        private set

    fun initialize() {
        PolyNametagConfig.preload()
        isEssential = OmniLoader.isLoaded("essential") && !OmniLoader.isLoaded("notsoessential")
        isPatcher = OmniLoader.isLoaded("patcher")
    }
}
