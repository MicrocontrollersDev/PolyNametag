package java.org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public abstract class Mixin_FixNametagRotation {
	// DEAD CODE
//    @ModifyArg(method = "renderNameTag", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;playerViewY:F"))
//    private float polynametag$fixNametagRotation(float yaw) {
//        if (PolyNametagConfig.isEnabled() && OmniPerspective.getCurrentPerspective() == OmniPerspective.THIRD_PERSON_FRONT) {
//            yaw *= -1.0F;
//        }
//
//        return yaw;
//    }
}
