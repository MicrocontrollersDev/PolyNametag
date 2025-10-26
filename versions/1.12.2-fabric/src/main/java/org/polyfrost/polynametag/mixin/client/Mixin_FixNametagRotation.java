package org.polyfrost.polynametag.mixin.client;

import dev.deftu.omnicore.api.client.options.OmniPerspective;
import net.minecraft.client.render.entity.EntityRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public abstract class Mixin_FixNametagRotation {
    @ModifyArg(method = "renderLabelIfPresent", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;yaw:F"))
    private float polynametag$fixNametagRotation(float yaw) {
        if (PolyNametagConfig.isEnabled() && OmniPerspective.getCurrentPerspective() == OmniPerspective.THIRD_PERSON_FRONT) {
            yaw *= -1.0F;
        }

        return yaw;
    }
}
