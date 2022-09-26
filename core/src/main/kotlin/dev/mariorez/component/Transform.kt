package dev.mariorez.component

import com.badlogic.gdx.math.Vector2
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class Transform(
    var position: Vector2 = Vector2()
) : Component<Transform> {
    override fun type() = Transform

    companion object : ComponentType<Transform>()
}
