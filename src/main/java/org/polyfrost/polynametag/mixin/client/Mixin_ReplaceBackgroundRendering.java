package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
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
    @WrapWithCondition(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V"))
    private boolean polynametag$cancelBegin(WorldRenderer instance, int glMode, VertexFormat format) {
        return !PolyNametagConfig.isEnabled();
    }

    @WrapWithCondition(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;pos(DDD)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private boolean polynametag$cancelPos(WorldRenderer instance, double x, double y, double z) {
        return !PolyNametagConfig.isEnabled();
    }

    @WrapWithCondition(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private boolean polynametag$cancelColor(WorldRenderer instance, float red, float green, float blue, float alpha) {
        return !PolyNametagConfig.isEnabled();
    }

    @WrapWithCondition(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;endVertex()V"))
    private boolean polynametag$cancelEndVertex(WorldRenderer instance) {
        return !PolyNametagConfig.isEnabled();
    }

    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void polynametag$replaceBackgroundRendering(Tessellator instance, Operation<Void> original, @Local(argsOnly = true) Entity entity) {
        if (PolyNametagConfig.isEnabled()) {
            System.out.println("MEOWOWORIUREENEMMEOW MRRP");
            NametagRenderer.drawBackground(OmniMatrixStacks.create(), entity);
        } else {
            original.call(instance);
        }
    }
}