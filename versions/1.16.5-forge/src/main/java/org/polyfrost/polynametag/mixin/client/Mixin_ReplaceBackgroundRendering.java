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

@Mixin(value = EntityRenderer.class, priority = 999)
public abstract class Mixin_ReplaceBackgroundRendering<T extends Entity> {
    @WrapOperation(
            method = "renderNameTag",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.20.1
                    //$$ target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I",
                    //#else
                    target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I",
                    //#endif
                    ordinal = 0
            )
    )
    private int polynametag$cancelBegin(
            Font instance,
            Component arg,
            float f,
            float g,
            int i,
            boolean bl,
            Matrix4f matrix4f,
            MultiBufferSource bufferSource,
            //#if MC >=1.20.1
            //$$ Font.DisplayMode displayMode,
            //#else
            boolean displayMode,
            //#endif
            int x,
            int y,
            Operation<Integer> original,
            @Local(argsOnly = true) PoseStack pose,
            @Local(argsOnly = true) Entity entity
    ) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.vanilla(pose), entity);
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
