package dev.mariorez.screen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.github.quillraven.fleks.world
import dev.mariorez.BaseScreen
import dev.mariorez.component.Render
import dev.mariorez.component.Transform
import dev.mariorez.system.RenderSystem
import ktx.assets.async.AssetStorage
import ktx.assets.disposeSafely

class FirstScreen(
    private val assets: AssetStorage
) : BaseScreen() {

    private val world = world {
        injectables {
            add(batch)
            add(camera)
        }

        systems {
            add(RenderSystem())
        }
    }

    init {
        world.entity {
            val turtle = assets.get<Texture>("turtle.png")
            it += Render(Sprite(turtle, turtle.width, turtle.height))
            it += Transform().apply {
                position.set(100f, 100f)
            }
        }
    }

    override fun render(delta: Float) {
        world.update(delta)
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        assets.disposeSafely()
    }
}
