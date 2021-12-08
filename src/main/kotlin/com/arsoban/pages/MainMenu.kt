package com.arsoban.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.arsoban.data.BotData
import com.arsoban.data.InterfaceColor
import com.arsoban.data.WhatIsOpened
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class MainMenu : KoinComponent {

    private val whatIsOpened: WhatIsOpened by inject(named("whatIsOpened"))

    private val interfaceColor: InterfaceColor by inject(named("colors"))

    private val botData: BotData by inject(named("botData"))

    @Composable
    fun mainMenu() {

        if (whatIsOpened.mainMenu.value) {

            Box(
                modifier = Modifier
                    .background(interfaceColor.backgroundColor)
                    .fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {

                    Row(

                    ) {

                        Box(
                            modifier = Modifier
                                .border(4.dp, Brush.linearGradient(listOf(interfaceColor.firstColor, interfaceColor.secondColor)), RoundedCornerShape(4.dp))
                                .size(300.dp)
                                .padding(10.dp)
                        ) {

                            Column {

                                val textSelectionColors = TextSelectionColors(
                                    handleColor = interfaceColor.secondColor,
                                    backgroundColor = interfaceColor.secondColor.copy(0.4F)
                                )

                                CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {

                                    OutlinedTextField(
                                        value = botData.token.value,
                                        onValueChange = { text ->
                                            botData.token.value = text
                                        },
                                        label = {
                                            Text("Token")
                                        },
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            textColor = interfaceColor.thirdColor,
                                            focusedLabelColor = interfaceColor.firstColor,
                                            unfocusedLabelColor = interfaceColor.thirdColor,
                                            cursorColor = interfaceColor.secondColor,
                                            focusedBorderColor = interfaceColor.secondColor
                                        ),
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                    )

                                }

                                Row {

                                    Button(
                                        onClick = {



                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = interfaceColor.firstColor
                                        ),
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        Text("Login as Bot")
                                    }

                                    Button(
                                        onClick = {



                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = interfaceColor.firstColor
                                        ),
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Text("Login as SelfBot")
                                    }

                                }

                            }

                        }

                    }

                }

            }


        }

    }

}