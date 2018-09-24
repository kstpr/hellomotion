package net.vectorworks.motion.core

import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.math.Vector3

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */
class Movable(
    override val id: Long,
    override var coordinates: Vector3,
    override val collisionShape: CollisionShape,
    val mass: Float = 1f) : Solid

