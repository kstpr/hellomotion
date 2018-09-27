package net.vectorworks.motion.kinematics

import net.vectorworks.motion.math.Vector3

/**
 * Created on 9/27/2018.
 *
 * Magnitude is in m/s
 * @author kpresnakov
 */

data class Velocity(val direction: Vector3) {

    fun magnitude() = direction.length()

    companion object {
        fun none(): Velocity = Velocity(Vector3.zero())
    }
}
