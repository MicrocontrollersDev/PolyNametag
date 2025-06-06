package org.polyfrost.polynametag.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import org.polyfrost.polynametag.NametagRenderer;
import org.polyfrost.polynametag.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

//#if MC > 1.17.1
//$$ import net.minecraft.client.renderer.MultiBufferSource;
//$$ import net.minecraft.network.chat.Component;
//$$ import net.minecraft.client.render.entity.EntityRenderer;
//$$ import net.minecraft.client.render.entity.state.EntityRenderState;
//$$ import net.minecraft.client.font.TextRenderer;
//$$ import net.minecraft.client.util.math.MatrixStack;
//$$ import com.llamalad7.mixinextras.sugar.Local;
//$$ import org.joml.Matrix4f;
//#endif


@Mixin(
    //#if MC < 1.17.1
    Render.class
    //#else
    //$$ EntityRenderer.class
    //#endif
)
public class Mixin_Render_ReplaceRendering<T extends Entity> {

    @WrapOperation(method = "renderLivingLabel", at = @At(value = "INVOKE", target =
            //#if MC < 1.17.1
            "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"
            //#else
            //$$ "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"
            //#endif
            )
    )
    private int polynametag$switchTextRendering(
            //#if MC < 1.17.1
            FontRenderer instance, String text, int x, int y, int color, Operation<Integer> original
            //#else
            //$$ TextRenderer instance, Text text, float x, float y, int color, boolean shadow, Matrix4f matrix4f, VertexConsumerProvider vertexConsumerProvider, TextRenderer.TextLayerType textLayerType, int backgroundColor, int packedLight, Operation<Integer> original, @Local(ordinal = 0, argsOnly = true) MatrixStack matrixStack
            //#endif
    ) {
        if (!PolyNametagConfig.INSTANCE.getEnabled()) {
            //#if MC < 1.17.1
            return original.call(instance, text, x, y, color);
            //#else
            //$$ return original.call(instance, text, x, y, color, shadow, matrix4f, vertexConsumerProvider, textLayerType, backgroundColor, packedLight);
            //#endif
        }

        //#if MC < 1.17.1
        return NametagRenderer.drawNametagString(instance, text, x, y, color);
        //#else
        //$$ return NametagRenderer.drawNametagString(matrixStack, instance, matrix4f, vertexConsumerProvider, textLayerType, backgroundColor, packedLight, text.getString(), x, y, color);
        //#endif
    }

    @Inject(method = "renderLivingLabel", at = @At("HEAD"), cancellable = true)
    private void polynametag$checkInventory(
            //#if MC < 1.17.1
            T entity, String str, double x, double y, double z, int maxDistance, CallbackInfo ci
            //#else
            //$$ EntityRenderState entityRenderState, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci
            //#endif
    ) {
        if (PolyNametagConfig.INSTANCE.getEnabled() && !PolyNametagConfig.INSTANCE.getShowInInventory() && Minecraft.getMinecraft().currentScreen instanceof GuiInventory) {
            ci.cancel();
        }
    }

    //#if MC < 1.17.1
    @ModifyArgs(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void polyNametag$changeScale(Args args) {
        if (!PolyNametagConfig.INSTANCE.getEnabled()) {
            return;
        }

        float scale = PolyNametagConfig.INSTANCE.getScale();
        args.set(0, ((float) args.get(0)) * scale);
        args.set(1, ((float) args.get(1)) * scale);
        args.set(2, ((float) args.get(2)) * scale);
    }
    //#else
    //$$ @Inject(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 0))
    //$$ public <S extends EntityRenderState> void polyNametag$changeScale(S entityRenderState, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
    //$$     // float scale = PolyNametagConfig.INSTANCE.getScale();
    //$$     float scale = 0.5f;
    //$$     matrixStack.scale(scale, scale, scale);
    //$$ }
    //#endif

    @ModifyArg(method = "renderLivingLabel", at = @At(value = "INVOKE",
            //#if MC < 1.17.1
            target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0), index = 1
            //#else
            //$$ target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"), index = 2
            //#endif
    )
    private float polynametag$modifyTranslateY(float y) {
        if (!PolyNametagConfig.INSTANCE.getEnabled()) {
            return y;
        }
        //#if MC < 1.17.1
        float scale = 1f;
        //#else
        //$$ float scale = -100f; // randomly chosen value. the old config values barely move the nametag at all
        //#endif
        return y + PolyNametagConfig.INSTANCE.getHeightOffset() * scale;
    }

    // TODO: 1.8 does all the rendering in this class, but in modern textRenderer handles all of this. we will need to create a custom textRenderer.draw
    //#if MC < 1.17.1
    @Inject(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void polynametag$replaceDefaultBackgroundRendering(T entity, String str, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        if (!PolyNametagConfig.INSTANCE.getEnabled()) {
            return;
        }

        NametagRenderer.drawBackground(entity);
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V"))
    private void polynametag$cancelDefaultBackgroundRendering$begin(WorldRenderer instance, int glMode, VertexFormat format) {
        if (PolyNametagConfig.INSTANCE.getEnabled()) {
            return;
        }

        instance.begin(glMode, format);
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;pos(DDD)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private WorldRenderer polynametag$cancelDefaultBackgroundRendering$pos(WorldRenderer instance, double x, double y, double z) {
        if (PolyNametagConfig.INSTANCE.getEnabled()) {
            return instance;
        }

        return instance.pos(x, y, z);
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;"))
    private WorldRenderer polynametag$cancelDefaultBackgroundRendering$color(WorldRenderer instance, float red, float green, float blue, float alpha) {
        if (PolyNametagConfig.INSTANCE.getEnabled()) {
            return instance;
        }

        return instance.color(red, green, blue, alpha);
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;endVertex()V"))
    private void polynametag$cancelDefaultBackgroundRendering$endVertex(WorldRenderer instance) {
        if (PolyNametagConfig.INSTANCE.getEnabled()) {
            return;
        }

        instance.endVertex();
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void polynametag$cancelDefaultBackgroundRendering(Tessellator instance) {
        if (PolyNametagConfig.INSTANCE.getEnabled()) {
            return;
        }

        instance.draw();
    }
    //#endif

}
