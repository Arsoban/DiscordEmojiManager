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
import app
import com.arsoban.data.BotData
import com.arsoban.data.InterfaceColor
import com.arsoban.data.WhatIsOpened
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.javacord.api.AccountType
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.CompletionException
import kotlin.concurrent.thread

class MainMenu : KoinComponent {

    private val whatIsOpened: WhatIsOpened by inject(named("whatIsOpened"))

    private val interfaceColor: InterfaceColor by inject(named("colors"))

    private val botData: BotData by inject(named("botData"))

    private val scaffoldState: ScaffoldState by inject(named("scaffoldState"))

    private val coroutineScope: CoroutineScope by inject(named("coroutineScope"))

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
//                                .border(4.dp, Brush.linearGradient(listOf(interfaceColor.firstColor, interfaceColor.secondColor)), RoundedCornerShape(4.dp))
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

                                Row(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                ) {



                                    Button(
                                        onClick = {

                                            thread {

                                                try {
                                                    var api = DiscordApiBuilder()
                                                        .setToken(botData.token.value)
                                                        .login().join()

                                                    val apiModule = module {
                                                        single(named("discordApi")) { api }
                                                    }

                                                    app!!.modules(apiModule)

                                                    whatIsOpened.mainMenu.value = false
                                                    whatIsOpened.botMenu.value = true
                                                } catch (exc: CompletionException) {

                                                    coroutineScope.launch {
                                                        scaffoldState.snackbarHostState.showSnackbar("Error, please specify the token")
                                                    }

                                                }
                                            }

                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = interfaceColor.firstColor
                                        ),
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Text("Login as Bot")
                                    }

                                    Button(
                                        onClick = {

                                            thread {

                                                try {

                                                    var api = DiscordApiBuilder()
                                                        .setAccountType(AccountType.CLIENT)
                                                        .setToken(botData.token.value)
                                                        .login().join()

                                                    val apiModule = module {
                                                        single(named("discordApi")) { api }
                                                    }

                                                    app!!.modules(apiModule)

                                                    whatIsOpened.mainMenu.value = false
                                                    whatIsOpened.botMenu.value = true

                                                } catch (exc: CompletionException) {

                                                    coroutineScope.launch {
                                                        scaffoldState.snackbarHostState.showSnackbar("Error, please specify the token")
                                                    }

                                                }
                                            }

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