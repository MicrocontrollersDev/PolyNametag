package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.command.LabelCommandRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.polyfrost.polynametag.LabelCommandMatrix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(LabelCommandRenderer.Commands.class)
public abstract class Mixin_SaveMatrixStackInLabelCommand {
    @WrapOperation(method = "add", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private <E> boolean polynametag$storeMatrixStack(List<E> instance, E labelCommand, Operation<Boolean> original, @Local(argsOnly = true) MatrixStack matrixStack) {
        ((LabelCommandMatrix) labelCommand).polynametag$setMatrixStack(matrixStack);
        return original.call(instance, labelCommand);
    }
}
