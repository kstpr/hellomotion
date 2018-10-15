package net.vectorworks.motion.collision

import net.vectorworks.motion.core.RigidBody

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */

interface Collider {
    fun hitTest(ray: Ray): HitTestResult
    fun hitTestAll(ray: Ray): List<HitTestResult>
    fun hitTestPlane(ray: Ray): HitTestResult
    fun hitTestWorldBounds(ray: Ray): HitTestResult

    fun overlapTest(collisionShape: RigidBody): RigidBody
    fun overlapTestAll(collisionShape: RigidBody): List<RigidBody>
    fun overlapTestPlane(collisionShape: RigidBody): Boolean // TODO more info
    fun overlapTestWorldBounds(collisionShape: RigidBody) : Boolean // TODO more info
}