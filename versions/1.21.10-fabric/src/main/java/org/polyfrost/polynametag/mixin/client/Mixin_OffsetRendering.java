package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.math.Vec3d;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public abstract class Mixin_OffsetRendering {
    @ModifyArg(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitLabel(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Vec3d;ILnet/minecraft/text/Text;ZIDLnet/minecraft/client/render/state/CameraRenderState;)V"), index = 1)
    private Vec3d polynametag$modifyTranslateY(Vec3d original) {
        if (PolyNametagConfig.isEnabled()) {
            original.add(0.0, PolyNametagConfig.getHeightOffset(), 0.0);
        }

        return original;
    }
}
