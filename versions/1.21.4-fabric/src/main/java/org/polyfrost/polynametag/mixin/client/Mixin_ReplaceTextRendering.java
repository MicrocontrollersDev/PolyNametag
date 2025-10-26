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
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ReplaceTextRendering {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 1))
    private int polynametag$renderCustomText(
            TextRenderer instance,
            Text text,
            float x,
            float y,
            int inColor,
            boolean shadow,
            Matrix4f matrix4f,
            VertexConsumerProvider buffer,
            TextRenderer.TextLayerType textLayerType,
            int backgroundColor,
            int light,
            Operation<Integer> original,
            @Local(argsOnly = true) MatrixStack matrices,
            @Local(argsOnly = true) EntityRenderState entityRenderState
    ) {
        if (PolyNametagConfig.isEnabled()) {
            final OmniColor color = new OmniColor(ColorFormat.ARGB, inColor).withAlpha(entityRenderState.sneaking ? 32 : 255);
            return NametagRenderer.drawNametagString(OmniMatrixStacks.vanilla(matrices), text.getString(), x, y, color);
        } else {
            return original.call(
                    instance,
                    text,
                    x,
                    y,
                    inColor,
                    shadow,
                    matrix4f,
                    buffer,
                    textLayerType,
                    backgroundColor,
                    light
            );
        }
    }

    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/state/EntityRenderState;sneaking:Z"))
    private boolean polynametag$renderCustomTextWhilstSneaking(EntityRenderState instance, Operation<Boolean> original) {
        if (PolyNametagConfig.isEnabled()) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
