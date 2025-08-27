package org.polyfrost.polynametag

//#if FABRIC
//$$ import net.fabricmc.api.ClientModInitializer
//#elseif FORGE
//#if MC >= 1.16.5
//$$ import net.minecraftforge.eventbus.api.IEventBus
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
//#else
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif
//#elseif NEOFORGE
//$$ import net.neoforged.bus.api.IEventBus
//$$ import net.neoforged.fml.common.Mod
//#endif

import org.polyfrost.polynametag.client.PolyNametagClient

//#if FORGE-LIKE
//#if MC >= 1.16.5
//$$ @Mod(PolyNametagConstants.ID)
//#else
@Mod(modid = PolyNametagConstants.ID, version = PolyNametagConstants.VERSION)
//#endif
//#endif
class PolyNametagEntrypoint
//#if FABRIC
//$$     : ClientModInitializer
//#endif
{

    //#if FORGE && MC >= 1.16.5
    //$$ init {
    //$$     setupForgeEvents(FMLJavaModLoadingContext.get().modEventBus)
    //$$ }
    //#elseif NEOFORGE
    //$$ constructor(modEventBus: IEventBus) {
    //$$     setupForgeEvents(modEventBus)
    //$$ }
    //#endif

    //#if FABRIC
    //$$ override
    //#elseif FORGE && MC <= 1.12.2
    @Mod.EventHandler
    //#endif
    fun onInitializeClient(
        //#if FORGE && MC <= 1.12.2
        event: FMLInitializationEvent
        //#endif
    ) {
        //#if MC <= 1.12.2
        if (!event.side.isClient) {
            return
        }
        //#endif

        PolyNametagClient.initialize()
    }

    //#if FORGE-LIKE && MC >= 1.16.5
    //$$ fun setupForgeEvents(modEventBus: IEventBus) {
    //$$     if (OmniLoader.isPhysicalClient) {
    //$$         onInitializeClient()
    //$$     }
    //$$ }
    //#endif
}
