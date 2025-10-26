package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.LabelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.polyfrost.polynametag.LabelCommandStorage;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LabelCommandRenderer.class)
public abstract class Mixin_ReplaceBackgroundRendering {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)V", ordinal = 0))
    private void polynametag$renderCustomBackground(
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
            @Local OrderedRenderCommandQueueImpl.LabelCommand labelCommand
    ) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(((LabelCommandStorage) (Object) labelCommand).polynametag$getMatrixStack(), text);
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
