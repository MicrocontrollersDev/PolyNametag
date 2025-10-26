package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.Render;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Render.class)
public abstract class Mixin_FixNametagRotation {
    @ModifyArg(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float polynametag$fixNametagRotation(float angle) {
        if (PolyNametagConfig.isEnabled()) {
            angle *= -1.0F;
        }

        return angle;
    }
}
