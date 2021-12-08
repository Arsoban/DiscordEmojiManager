package com.arsoban.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arsoban.data.EmojiData
import com.arsoban.data.InterfaceColor
import com.arsoban.data.WhatIsOpened
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.javacord.api.DiscordApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.io.File
import java.util.NoSuchElementException
import kotlin.concurrent.thread

class BotMenu : KoinComponent {

    private val whatIsOpened: WhatIsOpened by inject(named("whatIsOpened"))

    private val interfaceColor: InterfaceColor by inject(named("colors"))

    private val emojiData: EmojiData by inject(named("emojiData"))

    private val scaffoldState: ScaffoldState by inject(named("scaffoldState"))

    private val coroutineScope: CoroutineScope by inject(named("coroutineScope"))

    private val api: DiscordApi by inject(named("discordApi"))

    @Composable
    fun botMenu() {

        AnimatedVisibility(
            visible = whatIsOpened.botMenu.value
        ) {

            Box(
                modifier = Modifier
                    .background(interfaceColor.backgroundColor)
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {

                    Column(
                        modifier = Modifier
                            .padding(6.dp)
                    ) {

                        val selectionColors = TextSelectionColors(
                            handleColor = interfaceColor.secondColor,
                            backgroundColor = interfaceColor.secondColor.copy(0.4f)
                        )

                        CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {

                            OutlinedTextField(
                                value = emojiData.serverIdToDownloadEmojis.value,
                                onValueChange = { text ->
                                    emojiData.serverIdToDownloadEmojis.value = text
                                },
                                label = {
                                    Text("Server ID")
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = interfaceColor.firstColor,
                                    focusedLabelColor = interfaceColor.firstColor,
                                    focusedBorderColor = interfaceColor.secondColor,
                                    unfocusedLabelColor = interfaceColor.thirdColor
                                )
                            )

                        }

                        CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {

                            OutlinedTextField(
                                value = emojiData.pathToSave.value,
                                onValueChange = { text ->
                                    emojiData.pathToSave.value = text
                                },
                                label = {
                                    Text("Path to save emojis")
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = interfaceColor.firstColor,
                                    focusedLabelColor = interfaceColor.firstColor,
                                    focusedBorderColor = interfaceColor.secondColor,
                                    unfocusedLabelColor = interfaceColor.thirdColor
                                )
                            )

                        }

                        Button(
                            onClick = {

                                thread {

                                    try {

                                        val server = api.getServerById(emojiData.serverIdToDownloadEmojis.value).get()

                                        server.customEmojis.forEach { emoji ->

                                            val file = File(
                                                "${emojiData.pathToSave.value}/${emoji.name}.${
                                                    if (!emoji.image.url.toString().endsWith(".gif")) "png" else "gif"
                                                }"
                                            )

                                            file.writeBytes(emoji.image.asByteArray().get())

                                        }

                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("All emojis downloaded!")
                                        }

                                    } catch (exc: NoSuchElementException) {

                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("You don't specified server id or its wrong")
                                        }

                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = interfaceColor.firstColor
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Download emojis")
                        }

                    }


                    Column(
                        modifier = Modifier
                            .padding(6.dp)
                    ) {

                        val selectionColors = TextSelectionColors(
                            handleColor = interfaceColor.secondColor,
                            backgroundColor = interfaceColor.secondColor.copy(0.4f)
                        )

                        CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {

                            OutlinedTextField(
                                value = emojiData.serverIdToUploadEmojis.value,
                                onValueChange = { text ->
                                    emojiData.serverIdToUploadEmojis.value = text
                                },
                                label = {
                                    Text("Server ID")
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = interfaceColor.firstColor,
                                    focusedLabelColor = interfaceColor.firstColor,
                                    focusedBorderColor = interfaceColor.secondColor,
                                    unfocusedLabelColor = interfaceColor.thirdColor
                                )
                            )

                        }

                        CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {

                            OutlinedTextField(
                                value = emojiData.pathToUpload.value,
                                onValueChange = { text ->
                                    emojiData.pathToUpload.value = text
                                },
                                label = {
                                    Text("Path to upload emojis")
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = interfaceColor.firstColor,
                                    focusedLabelColor = interfaceColor.firstColor,
                                    focusedBorderColor = interfaceColor.secondColor,
                                    unfocusedLabelColor = interfaceColor.thirdColor
                                )
                            )

                        }

                        Button(
                            onClick = {

                                thread {
                                    try {
                                        val server = api.getServerById(emojiData.serverIdToUploadEmojis.value).get()

                                        val directory = File(emojiData.pathToUpload.value)

                                        if (directory.isDirectory) {
                                            directory.listFiles().forEach { file ->

                                                if (file.absolutePath.endsWith(".png") || file.absolutePath.endsWith(".gif")) {

                                                    server.createCustomEmojiBuilder().apply {
                                                        setName(file.name.dropLast(4))
                                                        setImage(file)
                                                    }.create().join()

                                                }

                                            }


                                            coroutineScope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar("All emojis uploaded!")
                                            }
                                        } else {
                                            coroutineScope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar("You specified not a directory")
                                            }
                                        }
                                    } catch (exc: NoSuchElementException) {
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("You don't specified server id or its wrong")
                                        }
                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = interfaceColor.firstColor
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Upload emojis")
                        }

                    }


                    Column(
                        modifier = Modifier
                            .padding(6.dp)
                    ) {

                        val selectionColors = TextSelectionColors(
                            handleColor = interfaceColor.secondColor,
                            backgroundColor = interfaceColor.secondColor.copy(0.4f)
                        )

                        CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {

                            OutlinedTextField(
                                value = emojiData.serverIdToDeleteEmojis.value,
                                onValueChange = { text ->
                                    emojiData.serverIdToDeleteEmojis.value = text
                                },
                                label = {
                                    Text("Server ID")
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = interfaceColor.firstColor,
                                    focusedLabelColor = interfaceColor.firstColor,
                                    focusedBorderColor = interfaceColor.secondColor,
                                    unfocusedLabelColor = interfaceColor.thirdColor
                                )
                            )

                        }

                        Button(
                            onClick = {

                                thread {
                                    try {

                                        val server = api.getServerById(emojiData.serverIdToDeleteEmojis.value).get()

                                        server.customEmojis.forEach { emoji ->

                                            emoji.delete().join()

                                        }

                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("Deleted all emojis!")
                                        }
                                    } catch (exc: NoSuchElementException) {
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("You don't specified server id or its wrong")
                                        }
                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = interfaceColor.firstColor
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Delete all emojis")
                        }

                    }

                }

            }

        }

    }

}