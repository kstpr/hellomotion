package net.vectorworks.motion.math.linearalgebra

import net.vectorworks.motion.math.ROUNDING_ERROR_100
import net.vectorworks.motion.math.roundingEquals
import net.vectorworks.motion.math.roundingNotEquals
import kotlin.math.sqrt

/**
 * Created on 9/23/2018.
 * @author kpresnakov
 */

data class Vector3(val x: Double, val y: Double, val z: Double) {

    companion object {
        fun zero(): Vector3 = Vector3(0.0, 0.0, 0.0)

        fun testForLinearDependence(vector1: Vector3, vector2: Vector3): Boolean {
            val testForZeroIndependence =
                { s: Double, t: Double ->
                    (roundingEquals(s, .0) && roundingNotEquals(t, .0)) ||
                        (roundingNotEquals(s, .0) && roundingEquals(t, .0))
                }

            if (testForZeroIndependence(vector1.x, vector2.x) ||
                testForZeroIndependence(vector1.y, vector2.y) ||
                testForZeroIndependence(vector1.z, vector2.z)) return false

            val getRatio = { s: Double, t: Double -> if (t == .0) .0 else s / t }

            val xRatio = getRatio(vector1.x, vector2.x)
            val yRatio = getRatio(vector1.y, vector2.y)
            val zRatio = getRatio(vector1.z, vector2.z)

            val nonZeroRatios = arrayOf(xRatio, yRatio, zRatio)
                .filter { ratio -> roundingNotEquals(ratio, 0.0) }

            return nonZeroRatios.areAllElementsEqual()
        }
    }
}

internal fun List<Double>.areAllElementsEqual(roundingError: Double = ROUNDING_ERROR_100): Boolean {
    return if (this.isEmpty()) {
        false
    } else {
        this.fold(this[0] to true)
        { (previous, wereEqual), current ->
            if (wereEqual) current to roundingEquals(previous, current, roundingError) else current to false
        }.second
    }
}

// Dot product
infix fun Vector3.dot(other: Vector3) = this.x * other.x + this.y * other.y + this.z * other.z

// Cross product
operator fun Vector3.times(other: Vector3) = Vector3(
    x = this.y * other.z - this.z * other.y,
    y = this.z * other.x - this.x * other.z,
    z = this.x * other.y - this.y * other.x
)

operator fun Vector3.minus(other: Vector3) = Vector3(this.x - other.x, this.y - other.y, this.z - other.z)

operator fun Vector3.plus(other: Vector3) = Vector3(this.x + other.x, this.y + other.x, this.z + other.z)

fun Vector3.length() = sqrt(x * x + y * y + z * z)

