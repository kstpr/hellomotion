package net.vectorworks.motion.collision

import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.linearalgebra.Vector3
import net.vectorworks.motion.math.linearalgebra.unaryMinus

/**
 * Created on 10/15/2018.
 * We only use compact simply connected manifolds with boundаries for collision shapes
 * @author kpresnakov
 */

interface CompactManifoldWithBoundary {
    fun isInside(point: Point): Boolean

    fun isOnBoundry(point: Point): Boolean

    fun isOutside(point: Point): Boolean = !isInside(point) && !isOnBoundry(point)

    fun rayHit(ray: Ray): Point?

    fun externalNormal(boundryPoint: Point): Vector3

    fun internalNormal(boundryPoint: Point) = -externalNormal(boundryPoint)
}
