package org.polyfrost.polynametag.mixin.client;

import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.OmniColor;
import dev.deftu.omnicore.api.color.OmniColors;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ReplaceTextRendering<T extends Entity> {
    @Inject(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;method_13427(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;FFFIFFZZ)V"))
    private void polynametag$replaceTextRendering(T entity, String text, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        if (PolyNametagConfig.isEnabled()) {
            OmniColor color = OmniColors.WHITE;
            if (entity.isSneaking()) {
                color = color.withAlpha(32);
            }

            NametagRenderer.drawNametagString(
                    OmniMatrixStacks.create(),
                    text,
                    (float) x,
                    (float) y,
                    color
            );
        }
    }
}
