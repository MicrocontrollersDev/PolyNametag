package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EntityRenderer.class)
public class Mixin_ApplyScaling {
    @ModifyArgs(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"))
    private void polynametag$applyScaling(Args args) {
        float scale = PolyNametagConfig.getScale();
        float x = args.get(0);
        float y = args.get(1);
        float z = args.get(2);
        args.set(0, x * scale);
        args.set(1, y * scale);
        args.set(2, z * scale);
    }
}
