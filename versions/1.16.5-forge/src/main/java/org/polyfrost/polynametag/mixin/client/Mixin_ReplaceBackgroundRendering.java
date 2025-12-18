package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public abstract class Mixin_ReplaceBackgroundRendering {
	@WrapOperation(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I", ordinal = 0))
    private int polynametag$renderCustomBackground(
			Font instance,
			Component text,
			float x,
			float y,
			int inColor,
			boolean shadow,
			Matrix4f matrix4f,
			MultiBufferSource vertexConsumerProvider,
			boolean seeThrough,
			int backgroundColor,
			int light,
			Operation<Integer> original,
            @Local(argsOnly = true) PoseStack poseStack,
            @Local(argsOnly = true) Entity entity
    ) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.vanilla(poseStack), entity);
            return 0;
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
