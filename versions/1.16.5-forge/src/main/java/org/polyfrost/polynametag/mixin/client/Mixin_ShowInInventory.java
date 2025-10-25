package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ShowInInventory<T extends Entity> {
    @WrapMethod(method = "renderNameTag")
    private void polynametag$checkInventory(T entity, Component text, PoseStack poseStack, MultiBufferSource multiBufferSource, int color, Operation<Void> original) {
        if (!PolyNametagConfig.isEnabled() || PolyNametagConfig.isShowOwnNametag() || !PolyNametagConfig.isShowInInventory() || !OmniScreens.isCurrentScreen(InventoryScreen.class)) {
            original.call(entity, text, poseStack, multiBufferSource, color);
        }
    }
}
