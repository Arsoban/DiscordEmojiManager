import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.arsoban.data.BotData
import com.arsoban.data.InterfaceColor
import com.arsoban.pages.SplashScreen
import com.arsoban.data.WhatIsOpened
import com.arsoban.pages.MainMenu
import com.arsoban.util.Fonts
import com.arsoban.util.ScaffoldInitialization
import com.arsoban.util.WindowInitialization
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

var app: KoinApplication? = null

@Composable
@Preview
fun App() {

    val whatIsOpened = WhatIsOpened(
        remember { mutableStateOf(true) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) }
    )

    val fonts = Fonts()

    val interfaceColor = InterfaceColor()

    val botData = BotData(
        remember { mutableStateOf("") }
    )

    val utilModule = module {
        single(named("whatIsOpened")) { whatIsOpened }
        single(named("colors")) { interfaceColor }
        single(named("fonts")) { fonts }
        single(named("botData")) { botData }
    }

    app!!.modules(
        utilModule
    )

    val scaffoldInitialization = ScaffoldInitialization()

    MaterialTheme {

        Scaffold(
            topBar = scaffoldInitialization.topAppBar
        ) {

            SplashScreen().splashScreen()

            MainMenu().mainMenu()

        }

    }

    WindowInitialization().initialize()
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DiscordEmojiManager",
        resizable = false,
        undecorated = true,
        state = WindowState(size = DpSize(800.dp, 800.dp))
    ) {

        val mainAppModule = module {
            single(named("window")) { this@Window }
            single(named("application")) { this@application }
        }

        app = startKoin {
            modules(mainAppModule)
        }

        App()
    }
}
