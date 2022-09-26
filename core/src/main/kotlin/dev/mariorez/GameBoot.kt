package dev.mariorez

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import dev.mariorez.screen.FirstScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync

class GameBoot : KtxGame<KtxScreen>() {

    private val assets = AssetStorage()

    companion object {
        const val WINDOW_HEIGHT = 560f
        const val WINDOW_WIDTH = 960f
    }

    override fun create() {
        KtxAsync.initiate()

        assets.apply {
            loadSync<Texture>("turtle.png").setFilter(Linear, Linear)
        }

        addScreen(FirstScreen(assets))
        setScreen<FirstScreen>()
    }
}
