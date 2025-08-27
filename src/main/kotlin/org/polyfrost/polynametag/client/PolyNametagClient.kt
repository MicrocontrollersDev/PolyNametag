package org.polyfrost.polynametag.client

import dev.deftu.omnicore.common.OmniLoader

object PolyNametagClient {
    var isPatcher = false
        private set

    @JvmStatic
    var isEssential = false
        private set

    fun initialize() {
        PolyNametagConfig.preload()

        isEssential = OmniLoader.isModLoaded("essential") && !OmniLoader.isModLoaded("notsoessential")
        isPatcher = OmniLoader.isModLoaded("patcher")
    }
}
