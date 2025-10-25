package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ShowInInventory<T extends Entity> {
    @WrapMethod(method = "renderLabelIfPresent")
    private void polynametag$checkInventory(Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, float tickDelta, Operation<Void> original) {
        if (!PolyNametagConfig.isEnabled() || PolyNametagConfig.isShowOwnNametag() || !PolyNametagConfig.isShowInInventory() || !OmniScreens.isCurrentScreen(InventoryScreen.class)) {
            original.call(entity, text, matrices, vertexConsumerProvider, light, tickDelta);
        }
    }
}
