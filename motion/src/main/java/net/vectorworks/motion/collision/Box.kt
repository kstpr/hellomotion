package net.vectorworks.motion.collision

import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.isRelativelyGreaterThan
import net.vectorworks.motion.math.linearalgebra.Vector3
import net.vectorworks.motion.math.linearalgebra.minus
import net.vectorworks.motion.math.linearalgebra.plus
import net.vectorworks.motion.math.linearalgebra.times

/**
 * Created on 10/6/2018.
 *
 * Defined by the point of intersection of the diagonals and a size vector with a diagonal direction and length with
 * only positive components.
 * @author kpresnakov
 */

class Box(center: Point = Point.zero(), val size: Vector3 = Vector3.zero()) :
    CollisionShape(center), CompactManifoldWithBoundary {

    private val extents = 0.5 * size

    override fun isInside(point: Point): Boolean {
        // as if centered at 0
        val fullyPositive = center + extents
        val fullyNegative = center - extents

        val isBettween: Double.(Double, Double) -> Boolean =
            { a, b -> this.isRelativelyGreaterThan(a) && b.isRelativelyGreaterThan(this) }

        return point.x.isBettween(fullyNegative.x, fullyPositive.x) &&
            point.y.isBettween(fullyNegative.y, fullyPositive.y) &&
            point.z.isBettween(fullyNegative.z, fullyPositive.z)
    }

    // TODO temp just for lolz
    fun correctCourse(point: Point, direction: Vector3): Vector3 {
        if (isInside(point)) return direction

        val fullyPositive = center + extents
        val fullyNegative = center - extents

        val isBettween: Double.(Double, Double) -> Boolean =
            { a, b -> this.isRelativelyGreaterThan(a) && b.isRelativelyGreaterThan(this) }

        val xInside = (point.x.isBettween(fullyNegative.x, fullyPositive.x))
        val yInside = (point.y.isBettween(fullyNegative.y, fullyPositive.y))
        val zInside = (point.z.isBettween(fullyNegative.z, fullyPositive.z))

        return Vector3(
            if (xInside) direction.x else -direction.x,
            if (yInside) direction.y else -direction.y,
            if (zInside) direction.z else -direction.z
        )
    }

    override fun isOnBoundry(point: Point): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun rayHit(ray: Ray): Point? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun externalNormal(boundryPoint: Point): Vector3 {
        TODO()
    }
}