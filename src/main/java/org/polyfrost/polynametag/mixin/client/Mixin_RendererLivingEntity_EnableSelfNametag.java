package org.polyfrost.polynametag.mixin.client;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
//#if MC >= 1.21.2
//$$ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.render.entity.LivingEntityRenderer;
//$$ import net.minecraft.entity.LivingEntity;
//#endif

@Mixin(RendererLivingEntity.class)
public class Mixin_RendererLivingEntity_EnableSelfNametag {

    //#if MC < 1.16.5
    // TODO: convert to a MixinExtras injection
    @Redirect(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    private Entity polynametag$enableSelfNametag(RenderManager instance) {
        if (!PolyNametagConfig.INSTANCE.isEnabled() || !PolyNametagConfig.INSTANCE.getShowOwnNametag()) {
            return instance.livingPlayer;
        }

        return null;
    }
    //#elseif MC >= 1.21.2
    //$$ @ModifyReturnValue(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("RETURN"))
    //$$ public <T extends LivingEntity> boolean showInThirdPerson(boolean original, T livingEntity) {
    //$$     if (livingEntity == MinecraftClient.getInstance().player && PolyNametagConfig.INSTANCE.isEnabled() && PolyNametagConfig.INSTANCE.getShowOwnNametag()) {
    //$$         return true;
    //$$     }
    //$$     return original;
    //$$ }
    //#endif

}
