package net.vectorworks.motion.core

import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.math.linearalgebra.Vector3
import net.vectorworks.motion.math.linearalgebra.plus
import net.vectorworks.motion.math.linearalgebra.times

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

fun Movable.recalculatePosition(timeDeltaInNanos: Long) {
    val timeDeltaInSeconds = timeDeltaInNanos / 1_000_000_000.0
    println("MOVABLE ==== recalcPosition timeDelta = $timeDeltaInSeconds s, velocity: $velocity, old coords: $position")
    println("======")
    position += (velocity * timeDeltaInSeconds)
    println("MOVABLE ==== recalcPosition, new coords: $position")
}