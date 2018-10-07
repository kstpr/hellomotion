package net.vectorworks.motion.math.linearalgebra

import net.vectorworks.motion.math.areAllElementsEqual
import net.vectorworks.motion.math.isCloseTo
import net.vectorworks.motion.math.isNotCloseTo
import kotlin.math.sqrt

/**
 * Created on 9/23/2018.
 * @author kpresnakov
 */

class Vector3(val x: Double, val y: Double, val z: Double) {

    companion object {
        fun zero(): Vector3 = Vector3(0.0, 0.0, 0.0)
    }

    fun equalsWithinToleranceTo(other: Vector3): Boolean {
        return this.x.isCloseTo(other.x) && this.y.isCloseTo(other.y) && this.z.isCloseTo(other.z)
    }

    override fun toString(): String {
        return "Vector($x, $y, $z)"
    }
}

// Dot product
infix fun Vector3.dot(other: Vector3) = this.x * other.x + this.y * other.y + this.z * other.z

// Cross product
infix fun Vector3.x(other: Vector3) = Vector3(
    x = this.y * other.z - this.z * other.y,
    y = this.z * other.x - this.x * other.z,
    z = this.x * other.y - this.y * other.x)

// Scalar multiplication
operator fun Vector3.times(scalar: Double) = Vector3(this.x * scalar, this.y * scalar, z = this.z * scalar)

operator fun Vector3.minus(other: Vector3) = Vector3(this.x - other.x, this.y - other.y, this.z - other.z)

operator fun Vector3.plus(other: Vector3) = Vector3(this.x + other.x, this.y + other.y, this.z + other.z)

fun Vector3.length() = sqrt(x * x + y * y + z * z)

fun Vector3.normalized(): Vector3 {
    val length = length()
    return if (length.isCloseTo(0.0)) {
        Vector3.zero()
    } else {
        Vector3(x / length, y / length, z / length)
    }
}

fun testForLinearDependence(vector1: Vector3, vector2: Vector3): Boolean {
    val testForZeroIndependence =
        { s: Double, t: Double ->
            (s.isCloseTo(.0) && t.isNotCloseTo(.0)) ||
                (s.isNotCloseTo(.0) && t.isCloseTo(.0))
        }

    if (testForZeroIndependence(vector1.x, vector2.x) ||
        testForZeroIndependence(vector1.y, vector2.y) ||
        testForZeroIndependence(vector1.z, vector2.z)) return false

    val getRatio = { s: Double, t: Double -> if (t == .0) .0 else s / t }

    val xRatio = getRatio(vector1.x, vector2.x)
    val yRatio = getRatio(vector1.y, vector2.y)
    val zRatio = getRatio(vector1.z, vector2.z)

    val nonZeroRatios = arrayOf(xRatio, yRatio, zRatio)
        .filter { ratio -> ratio.isNotCloseTo(0.0) }

    return nonZeroRatios.areAllElementsEqual()
}