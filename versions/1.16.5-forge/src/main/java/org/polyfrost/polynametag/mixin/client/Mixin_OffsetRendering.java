package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public class Mixin_OffsetRendering {
    @ModifyArg(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", ordinal = 0), index = 1)
    private double polynametag$modifyTranslateY(double original) {
        if (PolyNametagConfig.isEnabled()) {
            original += PolyNametagConfig.getHeightOffset();
        }

        return original;
    }
}
