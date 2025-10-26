package org.polyfrost.polynametag.mixin.client;

import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStack;
import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStacks;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.util.math.MatrixStack;
import org.polyfrost.polynametag.LabelCommandStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OrderedRenderCommandQueueImpl.LabelCommand.class)
public abstract class Mixin_ImplementLabelCommandMatrix implements LabelCommandStorage {
    @Unique
    private OmniMatrixStack polynametag$matrixStack = OmniMatrixStacks.create();

    @Unique
    private boolean polynametag$sneaking = false;

    @Override
    public OmniMatrixStack polynametag$getMatrixStack() {
        return polynametag$matrixStack;
    }

    @Override
    public void polynametag$setMatrixStack(MatrixStack matrixStack) {
        polynametag$matrixStack = OmniMatrixStacks.vanilla(matrixStack);
    }

    @Override
    public boolean polynametag$isSneaking() {
        return polynametag$sneaking;
    }

    @Override
    public void polynametag$setSneaking(boolean sneaking) {
        polynametag$sneaking = sneaking;
    }
}
