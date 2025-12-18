package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.deftu.omnicore.api.client.OmniClient;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ShowInInventory<T extends Entity> {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;getSquaredDistanceToCamera(Lnet/minecraft/entity/Entity;)D"))
    private double polynametag$hideNametagInInventory(EntityRenderDispatcher instance, Entity entity, Operation<Double> original) {
        if (PolyNametagConfig.isEnabled() &&
                !PolyNametagConfig.isShowInInventory() &&
                OmniScreens.getCurrentScreen() instanceof AbstractInventoryScreen &&
                entity == OmniClient.getPlayer()) {
            return Integer.MAX_VALUE;
        } else {
            return original.call(instance, entity);
        }
    }
}
