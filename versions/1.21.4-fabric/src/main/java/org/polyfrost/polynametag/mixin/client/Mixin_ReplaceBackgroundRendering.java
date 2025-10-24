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
public abstract class Mixin_ReplaceBackgroundRendering<T extends Entity> {
    @WrapOperation(
            method = "renderLabelIfPresent",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.20.1
                    target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I",
                    //#else
                    //$$ target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I",
                    //#endif
                    ordinal = 0
            )
    )
    private int polynametag$cancelBegin(
            TextRenderer instance,
            Text arg,
            float f,
            float g,
            int i,
            boolean bl,
            Matrix4f matrix4f,
            VertexConsumerProvider bufferSource,
            //#if MC >=1.20.1
            TextRenderer.TextLayerType displayMode,
            //#else
            //$$ boolean displayMode,
            //#endif
            int x,
            int y,
            Operation<Integer> original,
            @Local(argsOnly = true) MatrixStack pose,
            @Local(argsOnly = true) EntityRenderState entityRenderState
    ) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.vanilla(pose), entityRenderState);
            return 0;
        } else {
            return original.call(
                    instance,
                    arg,
                    f,
                    g,
                    i,
                    bl,
                    matrix4f,
                    bufferSource,
                    displayMode,
                    x, y
            );
        }
    }
}
