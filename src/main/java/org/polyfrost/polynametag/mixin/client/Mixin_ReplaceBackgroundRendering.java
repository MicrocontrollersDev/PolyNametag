package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.deftu.omnicore.api.client.render.stack.OmniPoseStacks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
//? if <= 1.21.8
import net.minecraft.client.renderer.entity.EntityRenderer;
//? if >= 1.21.10
/*import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;*/
import net.minecraft.network.chat.Component;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >= 1.21.10 {
/*@Mixin(NameTagFeatureRenderer.class)
*///?} else {
@Mixin(EntityRenderer.class)
//?}
public abstract class Mixin_ReplaceBackgroundRendering {
    @WrapOperation(method = /*? if >= 1.21.10 {*/ /*"render" *//*?} else {*/ "renderNameTag" /*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V", ordinal = 0))
    private void renderCustomBackground(Font instance, Component component, float x, float y, int color, boolean shadow, Matrix4f matrix4f, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int backgroundColor, int packedLight, Operation<Void> original /*? if <= 1.21.8 {*/, @Local(argsOnly = true) PoseStack pose /*?}*/) {
        if (PolyNametagConfig.isEnabled()) {
            PoseStack pose = new PoseStack();
            pose.pushPose();
            pose.mulPose(matrix4f);
            NametagRenderer.drawBackground(OmniPoseStacks.vanilla(/*? if >= 1.21.10 {*/ /*pose *//*?} else {*/ pose /*?}*/), component);
            pose.popPose();
        } else {
            original.call(instance, component, x, y, color, shadow, matrix4f, multiBufferSource, displayMode, backgroundColor, packedLight);
        }
    }
}