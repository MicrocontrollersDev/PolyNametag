package org.polyfrost.polynametag

import dev.deftu.omnicore.client.render.OmniMatrixStack
import dev.deftu.omnicore.client.render.OmniRenderState
import dev.deftu.omnicore.client.render.pipeline.DrawModes
import dev.deftu.omnicore.client.render.pipeline.OmniRenderPipeline
import dev.deftu.omnicore.client.render.pipeline.VertexFormats
import dev.deftu.omnicore.client.render.state.BlendEquation
import dev.deftu.omnicore.client.render.state.BlendFunction
import dev.deftu.omnicore.client.render.state.OmniManagedBlendState
import dev.deftu.omnicore.client.render.state.OmniManagedDepthState
import dev.deftu.omnicore.client.render.vertex.OmniBufferBuilder
import dev.deftu.omnicore.common.OmniIdentifier
import gg.essential.universal.UMatrixStack
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import org.lwjgl.opengl.GL11
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import org.polyfrost.polynametag.mixin.Accessor_FontRenderer_DrawString
import org.polyfrost.polynametag.render.EssentialBSManager
import org.polyfrost.polyui.unit.Vec2
import kotlin.math.cos
import kotlin.math.sin

//#if MC >= 1.17.1
//$$ import net.minecraft.client.util.math.MatrixStack
//$$ import net.minecraft.client.font.TextRenderer
//$$ import net.minecraft.client.render.VertexConsumerProvider
//$$ import org.joml.Matrix4f
//#endif

object NametagRenderer {

    private val points = arrayOf(Vec2(1f, 1f), Vec2(1f, -1f), Vec2(-1f, -1f), Vec2(-1f, 1f))
    private val translate = arrayOf(Vec2(1f, 0f), Vec2(0f, -1f), Vec2(-1f, 0f), Vec2(0f, 1f))

    var isDrawingIndicator = false
    private val essentialBSManager = EssentialBSManager()

    // TODO: send the pose stack instead of guiGraphics
    @JvmStatic
    fun drawNametagString(
            //#if MC >= 1.17.1
            //$$ matrixStack: MatrixStack, fontRenderer: TextRenderer, matrix4f: Matrix4f, vertexConsumerProvider: VertexConsumerProvider, textLayerType: TextRenderer.TextLayerType, backgroundColor: Int, packedLight: Int,
            //#else
            fontRenderer: FontRenderer,
            //#endif
            text: String, x: Float, y: Float, color: Int): Int {
        //#if MC < 1.17.1
        if (fontRenderer !is Accessor_FontRenderer_DrawString) {
            return 0
        }
        //#endif

        //#if MC < 1.17.1
        GlStateManager.pushMatrix()
        //#else
        //$$ matrixStack.push();
        //#endif
        return when (PolyNametagConfig.textType) { //TODO FULL SHADOW
            //#if MC < 1.17.1
            0 -> fontRenderer.invokeRenderString(text, x, y, color, false)
            1 -> fontRenderer.invokeRenderString(text, x, y, color, true)
            //#else
            //$$ 0 -> fontRenderer.draw(text, x, y, color, false, matrix4f, vertexConsumerProvider, textLayerType, backgroundColor, packedLight);
            //$$ 1 -> fontRenderer.draw(text, x, y, color, true, matrix4f, vertexConsumerProvider, textLayerType, backgroundColor, packedLight);
            //#endif
            else -> 0
        }.apply {
            //#if MC < 1.17.1
            GlStateManager.popMatrix()
            //#else
            //$$ matrixStack.pop();
            //#endif
        }
    }

    // what is the point of this method
//    @JvmStatic
//    fun drawNametagString(text: String, x: Float, y: Float, color: Int): Int {
//        return drawNametagString(mc.fontRendererObj, text, x, y, color)
//    }

    private val PIPELINE by lazy {
        OmniRenderPipeline.builderWithDefaultShader(
            identifier = OmniIdentifier.create("polynametag", "nametag_background"),
            vertexFormat = VertexFormats.POSITION_COLOR,
            mode = DrawModes.TRIANGLE_FAN,
        ).apply {

        }.build()
    }

    @JvmStatic
    fun drawBackground(x1: Double, x2: Double, entity: Entity
    //#if MC >= 1.17.1
    //$$ , matrixStack: MatrixStack
    //#endif
    ) {
        if (!PolyNametagConfig.background) {
            return
        }
        //#if MC >= 1.17.1
        //$$ val stack = OmniMatrixStack(matrixStack)
        //#endif

        //#if MC < 1.17.1
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        OmniRenderState.disableTexture2D()
        GL11.glPushMatrix()
        //#else
        //$$ stack.push();
        //#endif
        val realX1 = x1 - if (canDrawEssentialIndicator(entity)) 10 else 0
        //#if MC < 1.17.1
        GL11.glTranslated((realX1 + x2) / 2f, 3.5, 0.0)
        //#else
        //$$ stack.translate((realX1 + x2) / 2f, 3.5, 0.0)
        //#endif
        //#if MC < 1.17.1
        GL11.glBegin(GL11.GL_TRIANGLE_FAN)
        with(PolyNametagConfig.backgroundColor) {
            GL11.glColor4f(r / 255f, g / 255f, b / 255f, a.coerceAtMost(63) / 255f)
        }
        //#else
        //$$ val buffer = OmniBufferBuilder.create(DrawModes.LINES, VertexFormats.POSITION_COLOR)
        //#endif

        val halfWidth = (x2 - realX1) / 2f + PolyNametagConfig.paddingX.coerceIn(0f, 10f)
        val radius = if (PolyNametagConfig.rounded) PolyNametagConfig.cornerRadius.coerceIn(0f, 10f).coerceAtMost(4.5f + PolyNametagConfig.paddingY.coerceIn(0f, 10f)).coerceAtMost(halfWidth.toFloat()) else 0f
        val width = halfWidth - radius
        val distanceFromPlayer = entity.getDistanceToEntity(mc.thePlayer)
        val quality = ((distanceFromPlayer * 4 + 10).coerceAtMost(350f) / 4f).toInt()
        for (a in 0..3) {
            val (transX, transY) = translate[a]
            val (pointX, pointY) = points[a]
            val x = pointX * width
            val y = pointY * (4.5 + PolyNametagConfig.paddingY.coerceIn(0f, 10f) - radius)
            if (PolyNametagConfig.rounded) {
                for (b in 0 until 90 / quality) {
                    val angle = Math.toRadians((a * 90 + b * quality).toDouble())
                    //#if MC < 1.17.1
                    GL11.glVertex2d(x + sin(angle) * radius, y + cos(angle) * radius)
                    //#else
                    //$$ buffer
                    //$$     .vertex(stack, (x + sin(angle) * radius).toDouble(), (y + cos(angle) * radius).toDouble(), 0.0)
                    //$$     .color(PolyNametagConfig.backgroundColor.rgba)
                    //$$ .next()
                    //#endif
                }
            } else {
                //#if MC < 1.17.1
                GL11.glVertex2d(x, y)
                //#else
                //$$ buffer
                //$$     .vertex(stack, x.toDouble(), y.toDouble(), 0.0)
                //$$     .color(PolyNametagConfig.backgroundColor.rgba)
                //$$    .next()
                //#endif
            }
        }

        //#if MC < 1.17.1
        GL11.glEnd()
        GL11.glPopMatrix()
        OmniRenderState.enableTexture2D()
        GL11.glColor4f(1f, 1f, 1f, 1f)
        GL11.glDisable(GL11.GL_LINE_SMOOTH)
        //#else
        //$$ buffer.build()?.drawWithCleanup(PIPELINE)
        //$$ stack.pop();
        //#endif
    }

    @JvmStatic
    fun drawBackground(entity: Entity
        //#if MC >= 1.17.1
        //$$ , matrixStack: MatrixStack
        //#endif
    ) {
        //#if MC < 1.17.1
        val halfWidth = mc.fontRendererObj.getStringWidth(entity.displayName.formattedText) / 2 + 1.0
        //#else
        //$$ val halfWidth = mc.textRenderer.getWidth(entity.name.asOrderedText()) / 2 + 1.0
        //#endif
        //#if MC < 1.17.1
        drawBackground(-halfWidth, halfWidth, entity)
        //#else
        //$$ drawBackground(-halfWidth, halfWidth, entity, matrixStack)
        //#endif
    }

    fun drawIndicator(entity: Entity, string: String, light: Int) {
        if (entity !is AbstractClientPlayer) return
        isDrawingIndicator = true
        essentialBSManager.drawIndicator(UMatrixStack(), entity, string, light)
        isDrawingIndicator = false
    }

    @JvmStatic
    fun canDrawEssentialIndicator(entity: Entity): Boolean {
        return essentialBSManager.canDrawIndicator(entity)
    }

}
