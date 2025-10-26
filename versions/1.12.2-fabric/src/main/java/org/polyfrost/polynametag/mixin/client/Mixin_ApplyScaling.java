package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ApplyScaling {
    // TODO/FIX
//    @ModifyArgs(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;scale(FFF)V"))
//    private void polynametag$applyScaling(Args args) {
//        if (PolyNametagConfig.isEnabled()) {
//            final float scale = PolyNametagConfig.getScale();
//            args.set(0, ((float) args.get(0)) * scale);
//            args.set(1, ((float) args.get(1)) * scale);
//            args.set(2, ((float) args.get(2)) * scale);
//        }
//    }
}
