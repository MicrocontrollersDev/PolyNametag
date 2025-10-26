package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.render.entity.EntityRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public abstract class Mixin_OffsetRendering {
    @ModifyArg(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;method_13427(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;FFFIFFZZ)V"), index = 3)
    private float polynametag$modifyTranslateY(float original) {
        if (PolyNametagConfig.isEnabled()) {
            original += PolyNametagConfig.getHeightOffset();
        }

        return original;
    }
}
