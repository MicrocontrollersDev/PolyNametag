package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.OmniColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Render.class)
public abstract class Mixin_ReplaceTextRendering {
    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private int polynametag$replaceTextRendering(FontRenderer instance, String text, int x, int y, int color, Operation<Integer> original) {
        if (PolyNametagConfig.isEnabled()) {
            return NametagRenderer.drawNametagString(OmniMatrixStacks.create(), text, x, y, new OmniColor(color));
        } else {
            return original.call(instance, text, x, y, color);
        }
    }
}
