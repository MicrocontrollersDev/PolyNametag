package org.polyfrost.polynametag;

import dev.deftu.omnicore.api.client.render.stack.OmniMatrixStack;
import net.minecraft.client.util.math.MatrixStack;

public interface LabelCommandMatrix {
    OmniMatrixStack polynametag$getMatrixStack();

    void polynametag$setMatrixStack(MatrixStack matrixStack);
}
