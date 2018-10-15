package net.vectorworks.motion.core

import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */

data class Movable(
    override val id: Long,
    override var position: Vector3,
    override val collisionShape: CollisionShape,
    var velocity: Vector3 = Vector3.zero(),
    val mass: Float = 1f) : RigidBody

fun Movable.onChanged() {
    collisionShape.center = position
}