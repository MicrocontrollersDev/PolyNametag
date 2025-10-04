package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ApplyScaling {
    @ModifyArgs(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"))
    private void polynametag$applyScaling(Args args) {
        if (PolyNametagConfig.isEnabled()) {
            float scale = PolyNametagConfig.getScale();
            args.set(0, ((float) args.get(0)) * scale);
            args.set(1, ((float) args.get(1)) * scale);
            args.set(2, ((float) args.get(2)) * scale);
        }
    }
}
