package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RendererLivingEntity.class)
public abstract class Mixin_FixSneakingNametag {
    @WrapOperation(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isSneaking()Z"))
    private boolean polynametag$showCustomNametagWhilstSneaking(EntityLivingBase instance, Operation<Boolean> original) {
        if (PolyNametagConfig.isEnabled()) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
