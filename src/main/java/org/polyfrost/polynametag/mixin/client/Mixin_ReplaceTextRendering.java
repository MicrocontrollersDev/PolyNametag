package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.ColorFormat;
import dev.deftu.omnicore.api.color.OmniColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Render.class)
public abstract class Mixin_ReplaceTextRendering {
    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private int polynametag$replaceTextRendering(
            FontRenderer instance,
            String text,
            int x,
            int y,
            int inColor,
            Operation<Integer> original,
            @Local(argsOnly = true) Entity entity
    ) {
        if (PolyNametagConfig.isEnabled()) {
            final OmniColor color = new OmniColor(ColorFormat.ARGB, PolyNametagConfig.INSTANCE.getTextColor().getArgb()).withAlpha(entity.isSneaking() ? 32 : 255);
            return NametagRenderer.drawNametagString(OmniMatrixStacks.create(), text, x, y, color);
        } else {
            return original.call(instance, text, x, y, inColor);
        }
    }
}
