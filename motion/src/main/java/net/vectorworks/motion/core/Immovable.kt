package net.vectorworks.motion.core

import net.vectorworks.motion.collision.CollisionShape
import net.vectorworks.motion.math.Vector3

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */
data class Immovable(
    override val id: Long,
    override val coordinates: Vector3,
    override val collisionShape: CollisionShape) : Solid