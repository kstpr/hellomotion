package net.vectorworks.motion.math.geometry.objects.dim2

import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.linearalgebra.*
import kotlin.math.abs

/**
 * Created on 9/26/2018.
 *
 * Represented by the general form ax + by + cz + d = 0
 *
 * @author kpresnakov
 */

class Plane(val a: Double, val b: Double, val c: Double, val d: Double) {

    val normal = Vector3(a, b, c)

    fun distanceTo(point: Point) = abs(a * point.x + b * point.y + c * point.z + d) / point.length()

    override fun toString() = "Plane ${a}x + ${b}y + ${c}z + $d "

    companion object {

        fun fromPointAndNormal(point: Vector3, normal: Vector3): Plane {
            val a = normal.x
            val b = normal.y
            val c = normal.z
            val d = -(normal dot point)
            return Plane(a, b, c, d)
        }

        fun fromPointAndTwoVectors(point: Vector3, vector1: Vector3, vector2: Vector3): Plane {
            if (vector1 == Vector3.zero() || vector2 == Vector3.zero()) {
                throw IllegalArgumentException("Zero vector can't define a plane: v1: $vector1, v2: $vector2")
            }

            if (testForLinearDependence(vector1, vector2)) {
                throw IllegalArgumentException(
                    "Two linearly dependent vectors can't define a plane: v1: $vector1, v2: $vector2")
            }

            return Plane.fromPointAndNormal(point, vector1 x vector2)
        }

        fun fromThreePoints(point1: Vector3, point2: Vector3, point3: Vector3): Plane {
            val vector1 = point2 - point1
            val vector2 = point3 - point1

            if (testForLinearDependence(vector1, vector2))
                throw IllegalArgumentException("The 3 points must not be collinear")

            return Plane.fromPointAndTwoVectors(point1, vector1, vector2)
        }

        fun xz() = Plane(0.0, 1.0, 0.0, 0.0)
        fun xy() = Plane(0.0, 0.0, 1.0, 0.0)
        fun yz() = Plane(1.0, 0.0, 0.0, 0.0)
    }

}

