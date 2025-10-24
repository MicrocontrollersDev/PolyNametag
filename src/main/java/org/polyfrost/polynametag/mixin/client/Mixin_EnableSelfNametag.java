package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RendererLivingEntity.class)
public abstract class Mixin_EnableSelfNametag {
    @WrapOperation(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    private Entity polynametag$enableSelfNametag(RenderManager instance, Operation<Entity> original) {
        if (PolyNametagConfig.isEnabled() && PolyNametagConfig.isShowOwnNametag()) {
            return null;
        } else {
            return original.call(instance);
        }
    }
}
