package com.arsoban.util

import androidx.compose.ui.window.FrameWindowScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.awt.geom.RoundRectangle2D

class WindowInitialization : KoinComponent {

    private val window: FrameWindowScope by inject(named("window"))

    fun initialize() {

        window.window.shape = RoundRectangle2D.Double(0.0, 0.0, 800.0, 800.0, 50.0, 50.0)

    }

}