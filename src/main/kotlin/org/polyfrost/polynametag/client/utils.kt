package org.polyfrost.polynametag.client

import net.minecraft.client.renderer.OpenGlHelper

var lastBrightnessX
    //#if FORGE && MC <= 1.12.2
    get() = OpenGlHelper.lastBrightnessX
    set(value) {
        OpenGlHelper.lastBrightnessX = value
    }
    //#else
    //$$ = 0F
    //#endif

var lastBrightnessY
    //#if FORGE && MC <= 1.12.2
    get() = OpenGlHelper.lastBrightnessY
    set(value) {
        OpenGlHelper.lastBrightnessY = value
    }
    //#else
    //$$ = 0F
    //#endif
