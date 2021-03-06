import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "com.arsoban"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("io.insert-koin:koin-core:3.1.4")
    implementation("org.javacord:javacord:3.3.2")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.AppImage)
            packageName = "DiscordEmojiManager"
            packageVersion = "1.0.0"
            description = "DiscordEmojiManager"
            vendor = "Arsoban"

            windows {
                iconFile.set(project.file("src/main/resources/images/thinking.ico"))
                menuGroup = "DiscordEmojiManager"
            }

            linux {
                iconFile.set(project.file("src/main/resources/images/thinking.png"))
            }
        }
    }
}