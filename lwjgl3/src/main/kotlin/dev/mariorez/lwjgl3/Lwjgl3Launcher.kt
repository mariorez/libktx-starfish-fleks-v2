@file:JvmName("Lwjgl3Launcher")

package dev.mariorez.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import dev.mariorez.GameBoot
import dev.mariorez.GameBoot.Companion.WINDOW_HEIGHT
import dev.mariorez.GameBoot.Companion.WINDOW_WIDTH

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(GameBoot(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("starfish")
        setWindowedMode(WINDOW_WIDTH.toInt(), WINDOW_HEIGHT.toInt())
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
