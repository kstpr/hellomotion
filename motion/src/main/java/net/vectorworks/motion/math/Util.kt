package net.vectorworks.motion.math

import kotlin.math.abs
import kotlin.math.max

/**
 * Created on 9/29/2018.
 *
 * @author kpresnakov
 */

const val MACHINE_EPSILON = 1E-15
const val SHAKY_DELTA = 1E-20

fun Double.isCloseTo(other: Double,
                     relativeTolerance: Double = MACHINE_EPSILON,
                     absoluteTolerance: Double = SHAKY_DELTA): Boolean =
    abs(this - other) <= max(relativeTolerance * max(abs(this), abs(other)), absoluteTolerance)

fun Double.isNotCloseTo(other: Double, epsilon: Double = MACHINE_EPSILON) = !this.isCloseTo(other, epsilon)

fun Double.isRelativelyGreaterThan(b: Double, epsilon: Double = MACHINE_EPSILON) : Boolean {
    val diff = this - b
    return when {
        diff.isCloseTo(0.0, relativeTolerance = epsilon) -> false
        diff > 0 -> true
        else -> false
    }
}

// [a, b, c, d] -> [a ~ b, b ~ c, c ~ d], note that it may happen that a ~ b && b ~ c but a !~ c!
internal fun List<Double>.areAllElementsEqual(roundingError: Double = MACHINE_EPSILON): Boolean {
    return if (this.isEmpty()) {
        false
    } else {
        this.fold(this[0] to true)
        { (previous, wereEqual), current ->
            if (wereEqual) current to previous.isCloseTo(current, roundingError) else current to false
        }.second
    }
}

fun Double.isInRelativeRange(from: Double, to: Double) =
    this.isRelativelyGreaterThan(from) && to.isRelativelyGreaterThan(this)