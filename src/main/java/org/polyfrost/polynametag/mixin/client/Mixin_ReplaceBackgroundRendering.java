package org.polyfrost.polynametag.mixin.client;

import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.client.NametagRenderer;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public class Mixin_ReplaceBackgroundRendering<T extends Entity> {
    @Inject(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void polynametag$replaceBackgroundRendering(T entity, String text, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.create(), entity);
        }
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V"))
    private void polynametag$cancelBegin(WorldRenderer instance, int glMode, VertexFormat format) {
        if (!PolyNametagConfig.isEnabled()) {
            instance.begin(glMode, format);
        }
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;pos(DDD)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private WorldRenderer polynametag$cancelPos(WorldRenderer instance, double x, double y, double z) {
        if (PolyNametagConfig.isEnabled()) {
            return instance;
        } else {
            return instance.pos(x, y, z);
        }
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private WorldRenderer polynametag$cancelColor(WorldRenderer instance, float red, float green, float blue, float alpha) {
        if (PolyNametagConfig.isEnabled()) {
            return instance;
        } else {
            return instance.color(red, green, blue, alpha);
        }
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;endVertex()V"))
    private void polynametag$cancelEndVertex(WorldRenderer instance) {
        if (!PolyNametagConfig.isEnabled()) {
            instance.endVertex();
        }
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void polynametag$cancelDraw(Tessellator instance) {
        if (!PolyNametagConfig.isEnabled()) {
            instance.draw();
        }
    }
}
