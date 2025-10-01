package org.polyfrost.polynametag.mixin.client.levelhead;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Taken from Patcher under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * <a href="https://github.com/Sk1erLLC/Patcher/blob/master/LICENSE.md">LICENSE.md</a>
 */
@Pseudo
@Mixin(targets = "club.sk1er.mods.levelhead.render.AboveHeadRender", priority = 1001, remap = false)
public class Mixin_LevelheadTagCompatibility {
    @Redirect(
            method = "render(Lnet/minecraftforge/client/event/RenderLivingEvent$Specials$Post;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lclub/sk1er/mods/levelhead/render/AboveHeadRender;isSelf(Lnet/minecraft/entity/player/EntityPlayer;)Z",
                    ordinal = 1
            )
    ) @Dynamic("LevelHead")
    private boolean polynametag$modifySelfChecks(@Coerce Object instance, EntityPlayer player) {
        return !(PolyNametagConfig.INSTANCE.isEnabled() && PolyNametagConfig.INSTANCE.isShowOwnNametag()) && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getUniqueID().equals(player.getUniqueID());
    }
}
