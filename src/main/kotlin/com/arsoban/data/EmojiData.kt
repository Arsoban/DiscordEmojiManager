package com.arsoban.data

import androidx.compose.runtime.MutableState

class EmojiData(
    var serverIdToDownloadEmojis: MutableState<String>,
    var pathToSave: MutableState<String>,
    var serverIdToUploadEmojis: MutableState<String>,
    var pathToUpload: MutableState<String>
) {
}