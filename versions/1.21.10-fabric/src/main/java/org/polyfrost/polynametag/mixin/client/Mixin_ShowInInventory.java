package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ShowInInventory {
    @WrapMethod(method = "renderLabelIfPresent")
    private void polynametag$checkInventory(EntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState, Operation<Void> original) {
        if (!PolyNametagConfig.isEnabled() || PolyNametagConfig.isShowOwnNametag() || !PolyNametagConfig.isShowInInventory() || !OmniScreens.isCurrentScreen(InventoryScreen.class)) {
            original.call(state, matrices, queue, cameraRenderState);
        }
    }
}
