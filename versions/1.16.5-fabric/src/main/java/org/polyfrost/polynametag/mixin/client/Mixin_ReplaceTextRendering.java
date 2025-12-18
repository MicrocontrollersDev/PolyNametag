package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.ColorFormat;
import dev.deftu.omnicore.api.color.OmniColor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ReplaceTextRendering {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I", ordinal = 1))
    private int polynametag$renderCustomText(
            TextRenderer instance,
            Text text,
            float x,
            float y,
            int inColor,
            boolean shadow,
            Matrix4f matrix4f,
            VertexConsumerProvider vertexConsumerProvider,
            boolean seeThrough,
            int backgroundColor,
            int light,
            Operation<Integer> original,
            @Local(argsOnly = true) MatrixStack matrixStack
    ) {
        if (PolyNametagConfig.isEnabled()) {
            final OmniColor color = new OmniColor(ColorFormat.ARGB, PolyNametagConfig.INSTANCE.getTextColor().getArgb());
            return NametagRenderer.drawNametagString(OmniMatrixStacks.vanilla(matrixStack), text.getString(), x, y, color);
        } else {
            return original.call(
                    instance,
                    text,
                    x,
                    y,
                    inColor,
                    shadow,
                    matrix4f,
                    vertexConsumerProvider,
                    seeThrough,
                    backgroundColor,
                    light
            );
        }
    }
}
