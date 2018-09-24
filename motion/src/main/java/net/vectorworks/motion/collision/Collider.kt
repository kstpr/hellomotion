package net.vectorworks.motion.collision

import net.vectorworks.motion.core.Solid

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

    fun overlapTest(solid: Solid): Solid
    fun overlapTestAll(solid: Solid): List<Solid>
    fun overlapTestPlane(solid: Solid): Boolean // TODO more info
    fun overlapTestWorldBounds(solid: Solid) : Boolean // TODO more info
}