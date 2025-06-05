package org.polyfrost.polynametag.render.icon;

import gg.essential.Essential;
import gg.essential.config.EssentialConfig;
import gg.essential.connectionmanager.common.enums.ProfileStatus;
import gg.essential.data.OnboardingData;
import gg.essential.handlers.OnlineIndicator;
import gg.essential.universal.UMatrixStack;
import net.minecraft.entity.Entity;
//#if MC < 1.17.1
import net.minecraft.entity.player.EntityPlayer;
//#else
//$$ import net.minecraft.entity.player.PlayerEntity;
//#endif

public class Pre1354IconRender implements EssentialIconRender {
    @Override
    public void drawIndicator(UMatrixStack matrices, Entity entity, String str, int light) {
        OnlineIndicator.drawNametagIndicator(matrices, entity, str, light);
    }

    @Override
    public boolean canDrawIndicator(Entity entity) {
        if (OnboardingData.hasAcceptedTos() && EssentialConfig.INSTANCE.getShowEssentialIndicatorOnNametag() && entity instanceof
                //#if MC < 1.17.1
                EntityPlayer
                //#else
                //$$ PlayerEntity
                //#endif
        ) {
            return Essential.getInstance().getConnectionManager().getProfileManager().getStatus(
                    //#if MC < 1.17.1
                    ((EntityPlayer)
                    //#else
                    //$$ ((PlayerEntity)
                    //#endif
                            entity).getGameProfile().getId()) != ProfileStatus.OFFLINE;
        }
        return false;
    }
}
