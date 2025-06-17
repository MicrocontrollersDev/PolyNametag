package org.polyfrost.polynametag

//#if FORGE && MC < 1.16.5
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
//#elseif NEOFORGE
//$$ import net.neoforged.fml.ModLoadingContext
//$$ import net.neoforged.fml.common.Mod
//$$ import net.neoforged.neoforge.client.gui.IConfigScreenFactory
//#elseif FABRIC
//$$ import net.fabricmc.api.ClientModInitializer
//#endif

import dev.deftu.omnicore.common.OmniLoader

//#if FORGE && MC < 1.16.5
@Mod(modid = PolyNametag.ID, version = PolyNametag.VERSION, name = PolyNametag.NAME, modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter")
//#elseif NEOFORGE
//$$ @Mod(value = PolyNametag.ID)
//#endif
object PolyNametag
    //#if FABRIC
    //$$ : ClientModInitializer
    //#endif
{

    const val ID = "@MOD_ID@"
    const val NAME = "@MOD_NAME@"
    const val VERSION = "@MOD_VERSION@"

    var isPatcher = false
        private set

    var isEssential = false

    fun initialize() {
        PolyNametagConfig
    }

    fun postInitialize() {
        isEssential = OmniLoader.isModLoaded("essential") && !OmniLoader.isModLoaded("notsoessential")
        isPatcher = OmniLoader.isModLoaded("patcher")
    }

    //#if FORGE && MC < 1.16.5
    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        initialize()
    }

    @Mod.EventHandler
    fun onPostInit(event: FMLPostInitializationEvent) {
        postInitialize()
    }
    //#elseif FABRIC
    //$$ override fun onInitializeClient() {
    //$$     initialize()
    //$$     postInitialize()
    //$$ }
    //#elseif NEOFORGE
    //$$ fun PolyNametag() {
    //$$     initialize()
    //$$     postInitialize()
    //$$ }
    //#endif

}
