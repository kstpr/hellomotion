package net.vectorworks.motion.collision

import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */
data class HitTestResult(val distance: Float, val point: Vector3, val objectId: Long)