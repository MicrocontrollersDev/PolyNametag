package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Render.class)
public abstract class Mixin_ShowInInventory<T extends Entity> {
    @WrapMethod(method = "renderLivingLabel")
    private void polynametag$checkInventory(T entity, String str, double x, double y, double z, int maxDistance, Operation<Void> original) {
        if (!PolyNametagConfig.isEnabled() || PolyNametagConfig.isShowOwnNametag() || !PolyNametagConfig.isShowInInventory() || !OmniScreens.isCurrentScreen(GuiInventory.class)) {
            original.call(entity, str, x, y, z, maxDistance);
        }
    }
}
