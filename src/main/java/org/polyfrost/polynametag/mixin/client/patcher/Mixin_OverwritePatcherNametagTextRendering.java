package org.polyfrost.polynametag.mixin.client.patcher;

import dev.deftu.omnicore.api.client.render.OmniRenderingContext;
import dev.deftu.omnicore.api.color.OmniColor;
import net.minecraft.client.gui.FontRenderer;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "club.sk1er.patcher.hooks.NameTagRenderingHooks")
public abstract class Mixin_OverwritePatcherNametagTextRendering {
    @Dynamic("Patcher")
    @Inject(method = "drawNametagText", at = @At("HEAD"), remap = false, cancellable = true)
    private static void polyNametag$overwritePatcherDrawString(FontRenderer fontRenderer, String text, int x, int y, int color, CallbackInfoReturnable<Integer> cir) {
        if (!PolyNametagConfig.INSTANCE.isEnabled()) {
            return;
        }

        NametagRenderer.drawNametagString(OmniRenderingContext.from(), text, x, y, new OmniColor(color));
        cir.setReturnValue(0);
    }
}
