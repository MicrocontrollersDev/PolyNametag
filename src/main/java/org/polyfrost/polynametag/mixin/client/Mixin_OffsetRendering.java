package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.Render;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Render.class)
public class Mixin_OffsetRendering {
    @ModifyArg(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0), index = 1)
    private float polynametag$modifyTranslateY(float original) {
        if (PolyNametagConfig.isEnabled()) {
            original += PolyNametagConfig.getHeightOffset();
        }

        return original;
    }
}
