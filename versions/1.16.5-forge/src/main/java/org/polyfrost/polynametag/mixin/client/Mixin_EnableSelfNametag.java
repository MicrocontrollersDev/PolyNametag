package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntityRenderer.class)
public abstract class Mixin_EnableSelfNametag {
    @Redirect(method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getCameraEntity()Lnet/minecraft/world/entity/Entity;"))
    private Entity polynametag$checkOwnInvisibility(Minecraft instance) {
        if (!PolyNametagConfig.isEnabled() || !PolyNametagConfig.isShowOwnNametag()) {
            return null;
        } else {
            return instance.getCameraEntity();
        }
    }
}
