package net.vectorworks.motion.core

import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.kinematics.Velocity
import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */
data class Movable(
    override val id: Long,
    override var coordinates: Vector3,
    override val collisionShape: CollisionShape,
    var velocity: Velocity = Velocity.none(),
    val mass: Float = 1f) : Solid

