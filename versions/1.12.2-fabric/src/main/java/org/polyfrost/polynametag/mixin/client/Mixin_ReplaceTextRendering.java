package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.ColorFormat;
import dev.deftu.omnicore.api.color.OmniColor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ReplaceTextRendering<T extends Entity> {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;method_13427(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;FFFIFFZZ)V"))
    private void polynametag$renderCustomText(
            TextRenderer textRenderer,
            String text,
            float x,
            float y,
            float z,
            int offset,
            float yaw,
            float pitch,
            boolean isThirdperson,
            boolean isSneaking,
            Operation<Void> original,
            @Local(argsOnly = true) Entity entity
    ) {
        if (PolyNametagConfig.isEnabled()) {
            final OmniColor color = new OmniColor(ColorFormat.ARGB, PolyNametagConfig.INSTANCE.getTextColor().getArgb()).withAlpha(entity.isSneaking() ? 32 : 255);
            NametagRenderer.drawNametagString(OmniMatrixStacks.create(), text, x, y, color);
        } else {
            original.call(
                    textRenderer,
                    text,
                    x,
                    y,
                    z,
                    offset,
                    yaw,
                    pitch,
                    isThirdperson,
                    isSneaking
            );
        }
    }
}
