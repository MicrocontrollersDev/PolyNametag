package org.polyfrost.polynametag.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//#if MC > 1.17.1
//$$ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.render.entity.LivingEntityRenderer;
//$$ import net.minecraft.entity.LivingEntity;
//#endif

@Mixin(RendererLivingEntity.class)
public class Mixin_RendererLivingEntity_EnableSelfNametag {

    //#if MC < 1.17.1
    // TODO: nothing i do works wyvest pls help
    @ModifyExpressionValue(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    private Entity polynametag$enableSelfNametag(Entity original) {
        if (!PolyNametagConfig.INSTANCE.getEnabled() || !PolyNametagConfig.INSTANCE.getShowOwnNametag()) {
            return original;
        }

        return null;
    }
    //#else
    //$$ @ModifyReturnValue(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("RETURN"))
    //$$ public <T extends LivingEntity> boolean showInThirdPerson(boolean original, T livingEntity) {
    //$$     if (livingEntity == MinecraftClient.getInstance().player && PolyNametagConfig.INSTANCE.getEnabled() && PolyNametagConfig.INSTANCE.getShowOwnNametag()) {
    //$$         return true;
    //$$     }
    //$$     return original;
    //$$ }
    //#endif

}
