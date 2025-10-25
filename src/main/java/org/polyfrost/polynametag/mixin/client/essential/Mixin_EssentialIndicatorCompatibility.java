package org.polyfrost.polynametag.mixin.client.essential;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import gg.essential.universal.UMatrixStack;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Pseudo
@Mixin(targets = "gg.essential.handlers.OnlineIndicator")
public abstract class Mixin_EssentialIndicatorCompatibility {
    @Dynamic("Essential")
    @ModifyArgs(method = "drawNametagIndicator", at = @At(remap = false, value = "INVOKE", target = "Lgg/essential/render/TextRenderTypeVertexConsumer;color(IIII)Lgg/essential/render/TextRenderTypeVertexConsumer;"))
    private static void polyNametag$modifyNametagColor(Args args) {
        if (PolyNametagConfig.isEnabled()) {
            args.set(3, 0);
        }
    }

    @Dynamic("Essential")
    @WrapWithCondition(method = "drawNametagIndicator", at = @At("HEAD"))
    private static boolean skip(UMatrixStack matrixStack, @Coerce Object entity, String str, int light) {
        return !PolyNametagConfig.isEnabled() || !NametagRenderer.INSTANCE.isDrawingIndicator();
    }
}
