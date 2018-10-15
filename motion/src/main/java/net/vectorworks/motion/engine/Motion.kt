package net.vectorworks.motion.engine

import net.vectorworks.motion.collision.Collider
import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.core.Immovable
import net.vectorworks.motion.core.Movable
import net.vectorworks.motion.kinematics.VelocityMonitor
import net.vectorworks.motion.loop.PeriodicExecutor
import net.vectorworks.motion.math.linearalgebra.Vector3
import net.vectorworks.motion.time.TimeProvider

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
    private var previousTick: Long = -1L

    private lateinit var velocityMonitor: VelocityMonitor

    fun initialize(movables: List<Movable>, immovables: List<Immovable>, worldBounds: CollisionShape?) {
        this.movables.addAll(movables)
        this.immovables.addAll(immovables)
        this.worldBounds = worldBounds

        velocityMonitor = VelocityMonitor(movables, immovables, worldBounds)

        periodicExecutor = PeriodicExecutor()
    }

    fun registerMovable(movable: Movable) {
        movables.add(movable)
    }

    fun registerImmovable(immovable: Immovable) {
        immovables.add(immovable)
    }

    fun start(onWorldChangedListener: OnWorldChangedListener) {
        previousTick = TimeProvider.now()

        movables.randomizeVelocities()

        periodicExecutor.execute {
            val currentTick = TimeProvider.now()
            val timeDelta = currentTick - previousTick

            velocityMonitor.recalculatePositions(timeDelta)

            previousTick = currentTick

            onWorldChangedListener.onWorldChanged(movables.map{it.id to it.position})
        }
    }

    fun stop() = periodicExecutor.stop()

}

private fun List<Movable>.randomizeVelocities() {
    this.forEach{ it.velocity = Vector3(0.08, 0.00, 0.08) }
}
