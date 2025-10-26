package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.deftu.omnicore.api.client.OmniClient;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.math.Vec3d;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ShowInInventory {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/state/EntityRenderState;nameLabelPos:Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d polynametag$hideNametagInInventory(EntityRenderState instance, Operation<Vec3d> original) {
        final Screen screen = OmniScreens.getCurrentScreen();
        if (PolyNametagConfig.isEnabled() &&
                !PolyNametagConfig.isShowInInventory() &&
                (screen instanceof InventoryScreen || screen instanceof CreativeInventoryScreen) &&
                instance instanceof PlayerEntityRenderState playerEntityRenderState &&
                playerEntityRenderState.id == OmniClient.getPlayer().getId()) {
            return null;
        } else {
            return original.call(instance);
        }
    }
}
