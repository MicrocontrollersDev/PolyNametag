package org.polyfrost.polynametag.mixin.client;

import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public class Mixin_ShowInInventory<T extends Entity> {
    @Inject(method = "renderLivingLabel", at = @At("HEAD"), cancellable = true)
    private void polynametag$checkInventory(T entity, String text, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        if (
                PolyNametagConfig.isEnabled() &&
                !PolyNametagConfig.isShowOwnNametag() &&
                PolyNametagConfig.isShowInInventory() &&
                OmniScreens.isCurrentScreen(GuiInventory.class)
        ) {
            ci.cancel();
        }
    }
}
