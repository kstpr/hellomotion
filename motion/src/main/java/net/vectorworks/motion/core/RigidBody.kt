package net.vectorworks.motion.core

import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */
interface RigidBody {
    val id: Long
    val position: Vector3
    val collisionShape: CollisionShape
}