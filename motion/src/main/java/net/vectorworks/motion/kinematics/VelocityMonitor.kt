package net.vectorworks.motion.kinematics

import net.vectorworks.motion.collision.Box
import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.core.Immovable
import net.vectorworks.motion.core.Movable
import net.vectorworks.motion.math.linearalgebra.plus
import net.vectorworks.motion.math.linearalgebra.times

/**
 * Created on 10/6/2018.
 *
 * @author kpresnakov
 */

internal class VelocityMonitor(initialMovables: List<Movable>,
                               initialImmovables: List<Immovable>,
                               private val worldBounds: CollisionShape?) {

    private val movables = mutableListOf<Movable>()
    private val immovables = mutableListOf<Immovable>()

    init {
        movables.addAll(initialMovables)
        immovables.addAll(initialImmovables)
    }

    fun recalculatePositions(timeDeltaInNanos: Long) {
        movables.forEach { movable ->
            if (worldBounds is Box) {
                movable.velocity = worldBounds.correctCourse(movable.position, movable.velocity)
            }

            val timeDeltaInSeconds = timeDeltaInNanos / 1_000_000_000.0
            println("MOVABLE ==== recalcPosition timeDelta = $timeDeltaInSeconds s, " +
                "velocity: $movable.velocity, old coords: $movable.position")
            println("======")
            movable.position += (timeDeltaInSeconds * movable.velocity)
            println("MOVABLE ==== recalcPosition, new coords: $movable.position")
        }
    }

    fun addMovable(movable: Movable) = movables.add(movable)
    fun addImmovable(movable: Movable) = movables.add(movable)

}