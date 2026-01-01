package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.client.OmniClient;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
//? if >= 1.21.10
/*import net.minecraft.client.renderer.entity.state.AvatarRenderState;*/
import net.minecraft.client.renderer.entity.state.EntityRenderState;
//? if <= 1.21.8
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.phys.Vec3;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ShowInInventory<S extends EntityRenderState> {
    // TODO: broken on 1.21.10
    @WrapOperation(method = /*? if >= 1.21.10 {*/ /*"submitNameTag" *//*?} else {*/ "renderNameTag" /*?}*/, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;nameTagAttachment:Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 polynametag$hideNametagInInventory(EntityRenderState instance, Operation<Vec3> original, @Local(argsOnly = true) S arg) {
        if (PolyNametagConfig.isEnabled() &&
                !PolyNametagConfig.isShowInInventory() &&
                (OmniScreens.getCurrentScreen() instanceof InventoryScreen ||
                OmniScreens.getCurrentScreen() instanceof CreativeModeInventoryScreen) &&
                arg instanceof /*? if >= 1.21.10 {*/ /*AvatarRenderState *//*?} else {*/ PlayerRenderState /*?}*/ playerRenderState &&
                playerRenderState.id == OmniClient.getPlayer().getId()) {
            return null;
        } else {
            return original.call(instance);
        }
    }
}
