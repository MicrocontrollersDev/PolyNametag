package org.polyfrost.polynametag.client

import dev.deftu.omnicore.api.client.render.OmniTextRenderer
import dev.deftu.omnicore.api.client.render.TextShadowType
import dev.deftu.omnicore.api.client.render.pipeline.OmniRenderPipeline
import dev.deftu.omnicore.api.client.render.pipeline.OmniRenderPipelines
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStack
import dev.deftu.omnicore.api.client.render.vertex.roundedQuad
import dev.deftu.omnicore.api.color.OmniColor
import gg.essential.universal.UMatrixStack
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.entity.Entity
import org.polyfrost.polynametag.client.render.EssentialBSManager

object NametagRenderer {
    private val PIPELINE by lazy {
        OmniRenderPipelines.POSITION_COLOR_TRIANGLES
            .newBuilder()
            .setDepthTest(OmniRenderPipeline.DepthTest.LESS_OR_EQUAL)
            .build()
    }

    var isDrawingIndicator = false
    private val essentialBSManager = EssentialBSManager()

    @JvmStatic
    fun drawBackground(
        matrices: OmniMatrixStack,
        x1: Double, x2: Double,
        entity: Entity,
    ) {
        if (!PolyNametagConfig.background) {
            return
        }

        matrices.with {
            val baselineY = 3.5F

            val leftPad = if (canDrawEssentialIndicator(entity)) 10.0F else 0.0F
            val realX1 = (x1.toFloat() - leftPad)
            val realX2 = x2.toFloat()
            val span = (realX2 - realX1).coerceAtLeast(0.0F)
            if (span <= 0.0F) {
                return@with
            }

            val centerX = (realX1 + realX2) / 2.0F
            matrices.translate(centerX, baselineY, 0.01F)
            val color = PolyNametagConfig.backgroundColor.let { c -> OmniColor(c.r, c.g, c.b, c.a) }
            val halfWidth = (span / 2.0F) + PolyNametagConfig.paddingX.coerceIn(0.0F, 10.0F)
            val halfHeight = 4.5F + PolyNametagConfig.paddingY.coerceIn(0.0F, 10.0F)
            val radius = if (PolyNametagConfig.rounded) {
                PolyNametagConfig.cornerRadius
                    .coerceIn(0.0F, 10.0F)
                    .coerceAtMost(halfWidth)
                    .coerceAtMost(halfHeight)
            } else 0.0F

            val buffer = PIPELINE.createBufferBuilder()
            buffer.roundedQuad(
                stack = matrices,
                x = (-halfWidth).toDouble(),
                y = (-halfHeight).toDouble(),
                width = (halfWidth * 2.0F).toDouble(),
                height = (halfHeight * 2.0F).toDouble(),
                radius = radius,
                color = color,
                segmentScale = 1.5
            )
            buffer.buildOrNull()?.drawAndClose(PIPELINE)
        }
    }

    @JvmStatic
    fun drawBackground(
        matrices: OmniMatrixStack,
        entity: Entity,
    ) {
        val displayName = entity.displayName ?: return
        val halfWidth = OmniTextRenderer.width(
            //#if MC >= 1.16.5
            //$$ displayName.string
            //#else
            displayName.formattedText
            //#endif
        ) / 2 + 1.0
        drawBackground(matrices, -halfWidth, halfWidth, entity)
    }

    @JvmStatic
    fun drawNametagString(
        matrices: OmniMatrixStack,
        text: String,
        x: Float, y: Float,
        color: OmniColor,
    ): Int {
        return matrices.with {
            matrices.translate(0.0F, 0.0F, -0.01F)
            OmniTextRenderer.render(
                matrices, text, x, y, color, when (PolyNametagConfig.textType) {
                    0 -> TextShadowType.None
                    1 -> TextShadowType.Drop
                    2 -> TextShadowType.Outline(OmniColor((color.alpha / 4 shl 24)))
                    else -> throw IllegalStateException("Unexpected value: ${PolyNametagConfig.textType}")
                }
            )
        }
    }

    fun drawIndicator(entity: Entity, string: String, light: Int) {
        if (entity is AbstractClientPlayer) {
            isDrawingIndicator = true
            essentialBSManager.drawIndicator(UMatrixStack(), entity, string, light)
            isDrawingIndicator = false
        }
    }

    @JvmStatic
    fun canDrawEssentialIndicator(entity: Entity): Boolean {
        return essentialBSManager.canDrawIndicator(entity)
    }
}