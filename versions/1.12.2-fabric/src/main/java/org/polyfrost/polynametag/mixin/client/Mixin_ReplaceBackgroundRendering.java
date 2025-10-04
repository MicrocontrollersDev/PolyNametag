package org.polyfrost.polynametag.mixin.client;

import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ReplaceBackgroundRendering<T extends Entity> {
    @Inject(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;method_13427(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;FFFIFFZZ)V", shift = At.Shift.BEFORE), cancellable = true)
    private void polynametag$cancelBegin(T entity, String text, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        if (PolyNametagConfig.isEnabled()) {
            ci.cancel();
            NametagRenderer.drawBackground(OmniMatrixStacks.create(), entity);
        }
    }
}
