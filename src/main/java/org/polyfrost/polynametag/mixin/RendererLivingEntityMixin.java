package org.polyfrost.polynametag.mixin;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.polynametag.hooks.HooksKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public class RendererLivingEntityMixin {
    @Inject(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At("HEAD"))
    private void polyNametag$renderName(EntityLivingBase entity, double x, double y, double z, CallbackInfo callbackInfo) {
        HooksKt.callback(this, entity, x, y, z, callbackInfo);
    }
}
