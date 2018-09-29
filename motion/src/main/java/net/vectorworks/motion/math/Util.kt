package net.vectorworks.motion.math

import kotlin.math.abs

/**
 * Created on 9/29/2018.
 *
 * @author kpresnakov
 */

const val ROUNDING_ERROR_100 = 1E-100
const val ROUNDING_ERROR_50 = 1E-50

fun roundingEquals(a: Double, b: Double, epsilon: Double = ROUNDING_ERROR_100): Boolean = abs(a - b) < epsilon

fun roundingNotEquals(a: Double, b: Double, epsilon: Double = ROUNDING_ERROR_100) = !roundingEquals(a, b, epsilon)

fun roundingGreaterThan(a: Double, b: Double, epsilon: Double = ROUNDING_ERROR_100) = a - b > epsilon