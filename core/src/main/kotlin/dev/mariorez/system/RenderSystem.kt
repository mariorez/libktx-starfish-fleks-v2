package dev.mariorez.system

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import dev.mariorez.component.Render
import dev.mariorez.component.Transform
import ktx.graphics.use

class RenderSystem(
    private val batch: SpriteBatch = inject(),
    private val camera: OrthographicCamera = inject()
) : IteratingSystem(
    family { all(Render, Transform) }
) {

    override fun onTick() {
        batch.use(camera) {
            super.onTick()
        }
    }

    override fun onTickEntity(entity: Entity) {
        entity[Render].sprite.apply {
            setBounds(
                entity[Transform].position.x,
                entity[Transform].position.y,
                width,
                height
            )
            draw(batch)
        }
    }
}