package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.ColorFormat;
import dev.deftu.omnicore.api.color.OmniColor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.text.Text;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ReplaceTextRendering<T extends Entity> {
    @Redirect(
            method = "renderLabelIfPresent",
            at = @At(
                    value = "INVOKE",
                    //#if MC >=1.21.6
                    //$$ target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;L;II)V"
                    //#elseif MC >= 1.20.1
                    target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"
                    //#else
                    //$$ target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I"
                    //#endif
            )
    )
    private int polynametag$replaceTextRendering(
            TextRenderer instance,
            Text content,
            float x,
            float y,
            int color,
            boolean shadow,
            Matrix4f matrix,
            VertexConsumerProvider buffer,
            //#if MC >= 1.20.1
            TextRenderer.TextLayerType textLayerType,
            //#else
            //$$ boolean seeThrough,
            //#endif
            int backgroundColor,
            int light,
            @Local(argsOnly = true) MatrixStack matrices
    ) {
        if (PolyNametagConfig.isEnabled()) {
            return NametagRenderer.drawNametagString(
                    OmniMatrixStacks.vanilla(matrices),
                    content.getString(),
                    x,
                    y,
                    new OmniColor(ColorFormat.ARGB, color)
            );
        } else {
            return instance.draw(
                    content,
                    x,
                    y,
                    color,
                    shadow,
                    matrix,
                    buffer,
                    //#if MC >= 1.20.1
                    textLayerType,
                    //#else
                    //$$ seeThrough,
                    //#endif
                    backgroundColor,
                    light
            );
        }
    }
}
