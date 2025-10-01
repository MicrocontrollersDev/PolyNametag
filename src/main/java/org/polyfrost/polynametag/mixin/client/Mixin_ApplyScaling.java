package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.Render;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Render.class)
public class Mixin_ApplyScaling {
    @ModifyArgs(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void polynametag$applyScaling(Args args) {
        float scale = PolyNametagConfig.getScale();
        args.set(0, ((float) args.get(0)) * scale);
        args.set(1, ((float) args.get(1)) * scale);
        args.set(2, ((float) args.get(2)) * scale);
    }
}
