package org.polyfrost.polynametag.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class Mixin_ShowInInventory<T extends Entity> {
    @Inject(method = "renderNameTag", at = @At("HEAD"), cancellable = true)
    private void polynametag$checkInventory(T arg, Component arg2, PoseStack arg3, MultiBufferSource arg4, int k, CallbackInfo ci) {
        if (
                PolyNametagConfig.isEnabled() &&
                !PolyNametagConfig.isShowOwnNametag() &&
                PolyNametagConfig.isShowInInventory() &&
                OmniScreens.isCurrentScreen(InventoryScreen.class)
        ) {
            ci.cancel();
        }
    }
}
