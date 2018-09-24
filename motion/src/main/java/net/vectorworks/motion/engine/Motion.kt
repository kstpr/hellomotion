package net.vectorworks.motion.engine

import net.vectorworks.motion.collision.Collider
import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.core.Immovable
import net.vectorworks.motion.core.Movable

/**
 * Created on 9/23/2018.
 * The physics engine
 * @author kpresnakov
 */

class Motion(private val collider: Collider) {
    private val movables = mutableListOf<Movable>()
    private val immovables = mutableListOf<Immovable>()

    fun initialize(movables: List<Movable>, immovables: List<Immovable>, worldBounds: CollisionShape?) {

    }

    fun start() {

    }

    fun registerMovable(movable: Movable) {

    }
}