package dev.mariorez.screen

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.world
import dev.mariorez.Action
import dev.mariorez.Action.DOWN
import dev.mariorez.Action.LEFT
import dev.mariorez.Action.RIGHT
import dev.mariorez.Action.UP
import dev.mariorez.BaseScreen
import dev.mariorez.Sizes
import dev.mariorez.component.Player
import dev.mariorez.component.Render
import dev.mariorez.component.Transform
import dev.mariorez.system.InputSystem
import dev.mariorez.system.MovementSystem
import dev.mariorez.system.RenderSystem
import ktx.assets.async.AssetStorage
import ktx.assets.disposeSafely
import ktx.tiled.totalHeight
import ktx.tiled.totalWidth
import kotlin.properties.Delegates

class FirstScreen(
    private val sizes: Sizes,
    private val assets: AssetStorage
) : BaseScreen(sizes) {
    private val tiledMap = assets.get<TiledMap>("map.tmx")
    private val mapRenderer = OrthoCachedTiledMapRenderer(tiledMap).apply { setBlending(true) }
    private var player: Entity by Delegates.notNull()
    private val world = world {
        injectables {
            add(batch)
            add(camera)
            add(mapRenderer)
            add(sizes.apply {
                worldWidth = tiledMap.totalWidth().toFloat()
                worldHeight = tiledMap.totalHeight().toFloat()
            })
        }

        systems {
            add(InputSystem())
            add(MovementSystem())
            add(RenderSystem())
        }
    }

    init {
        buildControls()
        spawnPlayer()
    }

    override fun render(delta: Float) {
        world.update(delta)
        hudStage.draw()
    }

    override fun dispose() {
        super.dispose()
        mapRenderer.dispose()
        world.dispose()
        assets.disposeSafely()
    }

    private fun buildControls() {
        actionMap[Keys.UP] = UP
        actionMap[Keys.DOWN] = DOWN
        actionMap[Keys.LEFT] = LEFT
        actionMap[Keys.RIGHT] = RIGHT
        actionMap[Keys.W] = UP
        actionMap[Keys.S] = DOWN
        actionMap[Keys.A] = LEFT
        actionMap[Keys.D] = RIGHT
    }

    private fun spawnPlayer() {
        val turtle = assets.get<Texture>("turtle.png")
        player = world.entity {
            it += Player()
            it += Render(Sprite(turtle, turtle.width, turtle.height))
            it += Transform().apply {
                position.set(100f, 100f)
                acceleration = 400f
                deceleration = 250f
                maxSpeed = 150f
            }
        }
    }

    override fun doAction(action: Action) {
        with(world) {
            player[Player].apply {
                when (action) {
                    UP -> up = action.starting
                    DOWN -> down = action.starting
                    LEFT -> left = action.starting
                    RIGHT -> right = action.starting
                    else -> super.doAction(action)
                }
            }
        }
    }
}
