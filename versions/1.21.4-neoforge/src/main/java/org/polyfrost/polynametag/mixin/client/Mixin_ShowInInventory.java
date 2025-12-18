package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.client.OmniClient;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ShowInInventory<T extends Entity, S extends EntityRenderState> {
    @WrapOperation(method = "renderNameTag", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;nameTagAttachment:Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 polynametag$hideNametagInInventory(EntityRenderState instance, Operation<Vec3> original, @Local(argsOnly = true) S arg) {
        if (PolyNametagConfig.isEnabled() &&
                !PolyNametagConfig.isShowInInventory() &&
                OmniScreens.getCurrentScreen() instanceof InventoryScreen &&
                arg instanceof PlayerRenderState playerRenderState &&
				playerRenderState.id == OmniClient.getPlayer().getId()) {
            return null;
        } else {
            return original.call(instance);
        }
    }
}
