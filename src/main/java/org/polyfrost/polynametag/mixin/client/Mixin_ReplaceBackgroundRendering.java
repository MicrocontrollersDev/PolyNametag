package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
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

@Mixin(Render.class)
public abstract class Mixin_ReplaceBackgroundRendering {
    // TODO: Replace w/WrapWithCondition once Wyvest unfucks MixinExtras in 1.8
    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V"))
    private void polynametag$cancelBegin(WorldRenderer instance, int glMode, VertexFormat format, Operation<Void> original) {
        if (!PolyNametagConfig.isEnabled()) {
            original.call(instance, glMode, format);
        }
    }

    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;pos(DDD)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private WorldRenderer polynametag$cancelPos(WorldRenderer instance, double x, double y, double z, Operation<WorldRenderer> original) {
        if (PolyNametagConfig.isEnabled()) {
            return instance;
        } else {
            return original.call(instance, x, y, z);
        }
    }

    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private WorldRenderer polynametag$cancelColor(WorldRenderer instance, float red, float green, float blue, float alpha, Operation<WorldRenderer> original) {
        if (PolyNametagConfig.isEnabled()) {
            return instance;
        } else {
            return original.call(instance, red, green, blue, alpha);
        }
    }

    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;endVertex()V"))
    private void polynametag$cancelEndVertex(WorldRenderer instance, Operation<Void> original) {
        if (!PolyNametagConfig.isEnabled()) {
            original.call(instance);
        }
    }

    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void polynametag$replaceBackgroundRendering(Tessellator instance, Operation<Void> original, @Local(argsOnly = true) Entity entity) {
        if (PolyNametagConfig.isEnabled()) {
            NametagRenderer.drawBackground(OmniMatrixStacks.create(), entity);
        } else {
            original.call(instance);
        }
    }
}