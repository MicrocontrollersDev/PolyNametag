package org.polyfrost.polynametag.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.command.LabelCommandRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.polyfrost.polynametag.LabelCommandStorage;
import org.polyfrost.polynametag.client.PolyNametagConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(LabelCommandRenderer.Commands.class)
public abstract class Mixin_SaveMatrixStackInLabelCommand {
    @Unique
    private boolean polynametag$sneaking = false;

    @WrapOperation(method = "add", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private <E> boolean polynametag$storeMatrixStack(
            List<E> instance,
            E labelCommand,
            Operation<Boolean> original,
            @Local(argsOnly = true) MatrixStack matrixStack,
            @Local(argsOnly = true) boolean notSneaking
    ) {
        ((LabelCommandStorage) labelCommand).polynametag$setMatrixStack(matrixStack);
        ((LabelCommandStorage) labelCommand).polynametag$setSneaking(polynametag$sneaking);
        return original.call(instance, labelCommand);
    }

    // Disable sneaking logic
    @ModifyVariable(method = "add", at = @At("HEAD"), argsOnly = true)
    private boolean polynametag$renderCustomTextWhilstSneaking(boolean original) {
        if (PolyNametagConfig.isEnabled()) {
            polynametag$sneaking = !original;
            return true;
        } else {
            return original;
        }
    }
}
