package dev.mariorez

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.ESCAPE
import com.badlogic.gdx.Input.Keys.F11
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import dev.mariorez.Action.EXIT_FULLSCREEN
import dev.mariorez.Action.FULLSCREEN
import dev.mariorez.GameBoot.Companion.WINDOW_HEIGHT
import dev.mariorez.GameBoot.Companion.WINDOW_WIDTH
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

abstract class BaseScreen : KtxScreen {

    protected val batch = SpriteBatch()
    protected val camera = OrthographicCamera().apply {
        setToOrtho(false, WINDOW_WIDTH, WINDOW_HEIGHT)
    }
    protected val hudStage = Stage(FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT), batch)
    val actionMap = mutableMapOf<Int, Action>()

    init {
        actionMap[F11] = FULLSCREEN
        actionMap[ESCAPE] = EXIT_FULLSCREEN
    }


    open fun doAction(action: Action) {
        if (action.starting) {
            when (action) {
                FULLSCREEN -> Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)
                EXIT_FULLSCREEN -> Gdx.graphics.setWindowedMode(WINDOW_WIDTH.toInt(), WINDOW_HEIGHT.toInt())
                else -> {}
            }
        }
    }

    override fun show() {
        (Gdx.input.inputProcessor as InputMultiplexer).apply {
            addProcessor(hudStage)
        }
    }

    override fun hide() {
        (Gdx.input.inputProcessor as InputMultiplexer).apply {
            removeProcessor(hudStage)
        }
    }

    override fun resize(width: Int, height: Int) {
        hudStage.viewport.update(width, height, true)
    }

    override fun dispose() {
        hudStage.disposeSafely()
        batch.disposeSafely()
    }
}
