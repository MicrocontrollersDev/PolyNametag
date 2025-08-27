package org.polyfrost.polynametag.mixin;

import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FontRenderer.class)
public interface Accessor_FontRenderer_DrawString {

    //#if MC < 1.16.5
    @Invoker
    int invokeRenderString(String text, float x, float y, int color, boolean dropShadow);
    //#endif

}
