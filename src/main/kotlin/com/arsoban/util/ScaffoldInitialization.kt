package com.arsoban.util

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.FrameWindowScope
import com.arsoban.data.InterfaceColor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.system.exitProcess

class ScaffoldInitialization : KoinComponent {

    private val window: FrameWindowScope by inject(named("window"))

    private val interfaceColor: InterfaceColor by inject(named("colors"))

    private val fonts: Fonts by inject(named("fonts"))

    val topAppBar: @Composable () -> Unit = {

        TopAppBar(
            backgroundColor = interfaceColor.firstColor
        ) {

            window.WindowDraggableArea {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                    ) {

                        Box(

                        ) {

                            Text(
                                "DiscordEmojiManager",
                                modifier = Modifier
                                    .padding(14.dp),
                                fontSize = 26.sp,
                                fontFamily = fonts.firstFontFamily
                            )

                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            IconButton(
                                onClick = { exitProcess(0) },
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(70.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource("images/exit.png"),
                                    "Exit"
                                )
                            }
                        }

                    }

                }
            }

        }

    }

}