package java.org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.math.Matrix4f;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import dev.deftu.omnicore.api.color.ColorFormat;
import dev.deftu.omnicore.api.color.OmniColor;
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
public abstract class Mixin_ReplaceTextRendering<T extends Entity> {
    @WrapOperation(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I", ordinal = 1))
    private int polynametag$renderCustomText(
			Font textRenderer,
			Component text,
			float x,
			float y,
			int offset,
			boolean shadow,
			Matrix4f matrix4f,
			MultiBufferSource multiBufferSource,
			boolean seeThrough,
            int yaw,
			int pitch,
			Operation<Integer> original,
			@Local(argsOnly = true) Entity entity
    ) {
        if (PolyNametagConfig.isEnabled()) {
            final OmniColor color = new OmniColor(ColorFormat.ARGB, PolyNametagConfig.INSTANCE.getTextColor().getArgb()).withAlpha(entity.isCrouching() ? 32 : 255);
            return NametagRenderer.drawNametagString(OmniMatrixStacks.create(), text.getString(), x, y, color);
        } else {
            return original.call(
                    textRenderer,
                    text,
                    x,
                    y,
                    offset,
                    shadow,
                    matrix4f,
                    multiBufferSource,
                    seeThrough,
                    yaw,
					pitch
            );
        }
	}
}
