package net.vectorworks.motion.engine

import net.vectorworks.motion.collision.Collider
import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.core.Immovable
import net.vectorworks.motion.core.Movable
import net.vectorworks.motion.time.PeriodicExecutor

/**
 * Created on 9/23/2018.
 * The physics engine.
 * @author kpresnakov
 */

class Motion(private val collider: Collider) {
    private val movables = mutableListOf<Movable>()
    private val immovables = mutableListOf<Immovable>()

    private lateinit var periodicExecutor: PeriodicExecutor

    private var worldBounds: CollisionShape? = null

    fun initialize(movables: List<Movable>, immovables: List<Immovable>, worldBounds: CollisionShape?) {
        this.movables.addAll(movables)
        this.immovables.addAll(immovables)
        this.worldBounds = worldBounds

        periodicExecutor = PeriodicExecutor()
    }

    fun registerMovable(movable: Movable) {
        movables.add(movable)
    }

    fun registerImmovable(immovable: Immovable) {
        immovables.add(immovable)
    }

    fun start() {
        movables.randomizeVelocities()
        periodicExecutor.execute {

        }
    }

    fun stop() = periodicExecutor.stop()

}

private fun List<Movable>.randomizeVelocities() {
    this.forEach{ it.velocity = }
}
