package com.arsoban.pages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.arsoban.data.InterfaceColor
import com.arsoban.data.WhatIsOpened
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class SplashScreen : KoinComponent {

    private val whatIsOpened: WhatIsOpened by inject(named("whatIsOpened"))

    private val interfaceColor: InterfaceColor by inject(named("colors"))

    @Composable
    fun splashScreen() {

        if (whatIsOpened.splashScreen.value) {

            val scale = remember {
                Animatable(0F)
            }

            val alpha = remember {
                Animatable(1F)
            }

            Box(
                modifier = Modifier
                    .background(interfaceColor.backgroundColor)
                    .fillMaxSize()
            ) {

                Image(
                    painter = painterResource("images/thinking.svg"),
                    "Thinking",
                    modifier = Modifier
                        .scale(scale.value)
                        .alpha(alpha.value)
                        .align(Alignment.Center)
                )

            }

            LaunchedEffect(true){

                scale.animateTo(
                    targetValue = 1F,
                    animationSpec = tween(
                        durationMillis = 1600
                    )
                )

                alpha.animateTo(
                    targetValue = 0.0F,
                    animationSpec = tween(
                        durationMillis = 1600
                    )
                )

                whatIsOpened.splashScreen.value = false
                whatIsOpened.mainMenu.value = true
            }

        }

    }

}