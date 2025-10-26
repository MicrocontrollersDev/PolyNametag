package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.color.ColorFormat;
import dev.deftu.omnicore.api.color.OmniColor;
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
public abstract class Mixin_ReplaceTextRendering {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)V", ordinal = 1))
    private void polynametag$renderCustomText(
            TextRenderer instance,
            Text text,
            float x,
            float y,
            int inColor,
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
            LabelCommandStorage labelCommandStorage = (LabelCommandStorage) (Object) labelCommand;
            final OmniColor color = new OmniColor(ColorFormat.ARGB, PolyNametagConfig.INSTANCE.getTextColor().getArgb()).withAlpha(labelCommandStorage.polynametag$isSneaking() ? 32 : 255);
            NametagRenderer.drawNametagString(labelCommandStorage.polynametag$getMatrixStack(), text.getString(), x, y, color);
        } else {
            original.call(
                    instance,
                    text,
                    x,
                    y,
                    inColor,
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
