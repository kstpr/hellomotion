package net.vectorworks.motion.constraints.dim1

import net.vectorworks.motion.constraints.Constraint
import net.vectorworks.motion.math.Vector3

/**
 * Created on 9/26/2018.
 *
 * @author kpresnakov
 */
data class Line(val point: Vector3, val direction: Vector3) : Constraint