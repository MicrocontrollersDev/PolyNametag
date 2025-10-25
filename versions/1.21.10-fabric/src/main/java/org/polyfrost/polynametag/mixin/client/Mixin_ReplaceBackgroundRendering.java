package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityRenderer.class, priority = 999)
public abstract class Mixin_ReplaceBackgroundRendering<T extends Entity, S extends EntityRenderState> {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)V", ordinal = 0))
    private void polynametag$cancelBegin(
            TextRenderer instance,
            Text text,
            float x,
            float y,
            int color,
            boolean shadow,
            Matrix4f matrix4f,
            VertexConsumerProvider vertexConsumerProvider,
            TextRenderer.TextLayerType textLayerType,
            int backgroundColor,
            int light,
            Operation<Void> original,
            @Local(argsOnly = true) MatrixStack matrices,
            @Local(argsOnly = true) S entityRenderState
    ) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.vanilla(matrices), entityRenderState);
        } else {
            original.call(
                    instance,
                    text,
                    x,
                    y,
                    color,
                    shadow,
                    matrix4f,
                    vertexConsumerProvider,
                    textLayerType,
                    backgroundColor,
                    light
            );
        }
    }
}
