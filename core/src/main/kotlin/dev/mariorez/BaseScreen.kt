package dev.mariorez

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import dev.mariorez.GameBoot.Companion.WINDOW_HEIGHT
import dev.mariorez.GameBoot.Companion.WINDOW_WIDTH
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

abstract class BaseScreen : KtxScreen {

    protected val batch = SpriteBatch()
    protected val camera = OrthographicCamera().apply {
        setToOrtho(false, WINDOW_WIDTH, WINDOW_HEIGHT)
    }

    override fun dispose() {
        batch.disposeSafely()
    }
}
