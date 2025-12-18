package java.org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public abstract class Mixin_OffsetRendering {
    @ModifyArg(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V"), index = 1)
    private double polynametag$modifyTranslateY(double original, @Local(argsOnly = true) Entity entity) {
        if (PolyNametagConfig.isEnabled()) {
            original += PolyNametagConfig.getHeightOffset();
            if (entity.isCrouching()) {
                original -= 0.5F;
            }
        }

        return original;
    }
}
