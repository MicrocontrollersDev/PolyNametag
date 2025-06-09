package org.polyfrost.polynametag.mixin;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.PolyNametagConfig;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
//#if MC > 1.17.1
//$$ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.client.render.entity.LivingEntityRenderer;
//$$ import net.minecraft.entity.LivingEntity;
//#endif

@Debug(export = true)
@Mixin(RendererLivingEntity.class)
public class Mixin_RendererLivingEntity_EnableSelfNametag {

    //#if MC < 1.17.1
    // TODO: convert to a MixinExtras injection
    @Redirect(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    private Entity polynametag$enableSelfNametag(RenderManager instance) {
        if (!PolyNametagConfig.INSTANCE.getEnabled() || !PolyNametagConfig.INSTANCE.getShowOwnNametag()) {
            return instance.livingPlayer;
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
