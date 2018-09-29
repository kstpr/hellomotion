package net.vectorworks.motion.math.geometry.objects.dim0

import net.vectorworks.motion.math.geometry.objects.dim1.Line
import net.vectorworks.motion.math.geometry.objects.dim2.Plane
import net.vectorworks.motion.math.linearalgebra.Vector3
import net.vectorworks.motion.math.roundingEquals
import net.vectorworks.motion.math.roundingGreaterThan

/**
 * Created on 9/30/2018.
 *
 * @author kpresnakov
 */
typealias Point = Vector3

fun Point.belongsToLine(line: Line) {

}

fun Point.isInPositiveHalfSpace(plane: Plane) =
    roundingGreaterThan(plane.a * x + plane.b * y + plane.c * z + plane.d, 0.0)

fun Point.belongsToPlane(plane: Plane) =
    roundingEquals(plane.a * x + plane.b * y + plane.c * z + plane.d, 0.0)