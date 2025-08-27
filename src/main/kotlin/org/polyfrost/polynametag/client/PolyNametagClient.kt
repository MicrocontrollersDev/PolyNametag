package org.polyfrost.polynametag.client

import dev.deftu.omnicore.common.OmniLoader
import org.polyfrost.polynametag.client.PolyNametagConfig

object PolyNametagClient {
    @JvmStatic
    var isEssential = false
        private set

    var isPatcher = false
        private set

    fun initialize() {
        PolyNametagConfig.preload()
        isEssential = OmniLoader.isModLoaded("essential") && !OmniLoader.isModLoaded("notsoessential")
        isPatcher = OmniLoader.isModLoaded("patcher")
    }
}
