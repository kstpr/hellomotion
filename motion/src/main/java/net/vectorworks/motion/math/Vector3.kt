package net.vectorworks.motion.math

import kotlin.math.sqrt

/**
 * Created on 9/23/2018.
 * JAVA DEPENDENT
 * @author kpresnakov
 */

data class Vector3(val x: Double, val y: Double, val z: Double) {

    fun length() = sqrt(x * x + y * y + z * z)

    companion object {
        fun zero(): Vector3 = Vector3(0.0, 0.0, 0.0)
    }
}