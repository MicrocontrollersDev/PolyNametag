package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityRenderer.class, priority = 999)
public abstract class Mixin_ReplaceBackgroundRendering<T extends Entity> {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I", ordinal = 0))
    private int polynametag$renderCustomBackground(
            TextRenderer instance,
            Text text,
            float x,
            float y,
            int color,
            boolean shadow,
            Matrix4f matrix4f,
            VertexConsumerProvider vertexConsumerProvider,
            boolean unknownArgument,
            int backgroundColor,
            int light,
            Operation<Integer> original,
            @Local(argsOnly = true) MatrixStack matrixStack,
            @Local(argsOnly = true) Entity entity
    ) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.vanilla(matrixStack), entity);
            return 0;
        } else {
            return original.call(
                    instance,
                    text,
                    x,
                    y,
                    color,
                    shadow,
                    matrix4f,
                    vertexConsumerProvider,
                    unknownArgument,
                    backgroundColor,
                    light
            );
        }
    }
}
