package net.vectorworks.motion.math.geometry.objects.dim1

import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 9/26/2018.
 *
 * @author kpresnakov
 */
data class Line(val point: Point, val direction: Vector3)
