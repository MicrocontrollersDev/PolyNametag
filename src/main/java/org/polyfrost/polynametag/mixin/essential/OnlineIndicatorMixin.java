package org.polyfrost.polynametag.mixin.essential;

import gg.essential.universal.UMatrixStack;
import org.polyfrost.polynametag.PolyNametag;
import org.polyfrost.polynametag.config.ModConfig;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "gg.essential.handlers.OnlineIndicator")
public class OnlineIndicatorMixin {

    @Dynamic("Essential")
    @Inject(method = "drawNametagIndicator", at = @At("HEAD"), cancellable = true)
    private static void skip(UMatrixStack matrixStack, @Coerce Object entity, String str, int light, CallbackInfo ci) {
        if (!ModConfig.INSTANCE.enabled) return;
        if (!PolyNametag.INSTANCE.getDrawingIndicator()) ci.cancel();
    }

    @Dynamic("Essential")
    @Inject(method = "getTextBackgroundOpacity", at = @At("HEAD"), cancellable = true)
    private static void polyNametag$removeEssentialBackground(CallbackInfoReturnable<Integer> cir) {
        if (!ModConfig.INSTANCE.enabled) return;
        cir.setReturnValue(0);
    }
}
