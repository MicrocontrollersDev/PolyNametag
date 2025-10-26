package org.polyfrost.polynametag.mixin.client;

import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStack;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.util.math.MatrixStack;
import org.polyfrost.polynametag.LabelCommandMatrix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OrderedRenderCommandQueueImpl.LabelCommand.class)
public abstract class Mixin_ImplementLabelCommandMatrix implements LabelCommandMatrix {
    @Unique
    private OmniMatrixStack polynametag$matrixStack = OmniMatrixStacks.create();

    @Override
    public OmniMatrixStack polynametag$getMatrixStack() {
        return polynametag$matrixStack;
    }

    @Override
    public void polynametag$setMatrixStack(MatrixStack matrixStack) {
        polynametag$matrixStack = OmniMatrixStacks.vanilla(matrixStack);
    }
}
