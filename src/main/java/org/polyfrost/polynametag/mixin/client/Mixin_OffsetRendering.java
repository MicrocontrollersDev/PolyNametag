package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Render.class)
public abstract class Mixin_OffsetRendering {
    @ModifyArg(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0), index = 1)
    private float polynametag$modifyTranslateY(float original, @Local(argsOnly = true) Entity entity) {
        if (PolyNametagConfig.isEnabled()) {
            original += PolyNametagConfig.getHeightOffset();
            if (entity.isSneaking()) {
                original -= 0.5F;
            }
        }

        return original;
    }
}
