package org.polyfrost.polynametag.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, priority = 999)
public class Mixin_ReplaceBackgroundRendering<T extends Entity> {
    @Inject(
            method = "renderNameTag",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.20.1
                    //$$ target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I",
                    //#else
                    target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I",
                    //#endif
                    shift = At.Shift.BEFORE
            )
    )
    private void polynametag$cancelBegin(
            T entity,
            Component content,
            PoseStack pose,
            MultiBufferSource bufferSource,
            int packedLight,
            //#if MC >= 1.21.1
            //$$ float tickDelta,
            //#endif
            CallbackInfo ci
    ) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.vanilla(pose), entity);
        }
    }
}
