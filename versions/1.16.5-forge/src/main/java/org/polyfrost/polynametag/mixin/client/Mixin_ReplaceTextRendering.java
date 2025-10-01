package org.polyfrost.polynametag.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.ColorFormat;
import dev.deftu.omnicore.api.color.OmniColor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class Mixin_ReplaceTextRendering<T extends Entity> {
    @Redirect(
            method = "renderNameTag",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.20.1
                    //$$ target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"
                    //#else
                    target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I"
                    //#endif
            )
    )
    private int polynametag$replaceTextRendering(
            Font instance,
            Component content,
            float x,
            float y,
            int color,
            boolean shadow,
            Matrix4f matrix,
            MultiBufferSource buffer,
            //#if MC >= 1.20.1
            //$$ TextRenderer.TextLayerType textLayerType,
            //#else
            boolean seeThrough,
            //#endif
            int backgroundColor,
            int light,

            T entity,
            Component displayName,
            PoseStack matrices,
            MultiBufferSource outerBuffers,
            int outerPackedLight
    ) {
        if (!PolyNametagConfig.isEnabled()) {
            return instance.drawInBatch(
                    content,
                    x,
                    y,
                    color,
                    shadow,
                    matrix,
                    buffer,
                    //#if MC >= 1.20.1
                    //$$ textLayerType,
                    //#else
                    seeThrough,
                    //#endif
                    backgroundColor,
                    light
            );
        }

        return NametagRenderer.drawNametagString(OmniMatrixStacks.vanilla(matrices), content.getString(), x, y, new OmniColor(ColorFormat.ARGB, color));
    }
}
