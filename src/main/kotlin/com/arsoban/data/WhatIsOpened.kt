package com.arsoban.data

import androidx.compose.runtime.MutableState

data class WhatIsOpened(
    var splashScreen: MutableState<Boolean>,
    var mainMenu: MutableState<Boolean>,
    var botMenu: MutableState<Boolean>
)
