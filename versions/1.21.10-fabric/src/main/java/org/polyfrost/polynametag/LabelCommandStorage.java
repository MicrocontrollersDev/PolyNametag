package org.polyfrost.polynametag;

import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStack;
import net.minecraft.client.util.math.MatrixStack;

public interface LabelCommandStorage {
    OmniMatrixStack polynametag$getMatrixStack();

    void polynametag$setMatrixStack(MatrixStack matrixStack);

    boolean polynametag$isSneaking();

    void polynametag$setSneaking(boolean sneaking);
}
