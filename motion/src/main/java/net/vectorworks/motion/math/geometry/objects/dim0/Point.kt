package net.vectorworks.motion.math.geometry.objects.dim0

import net.vectorworks.motion.math.geometry.objects.dim1.Line
import net.vectorworks.motion.math.geometry.objects.dim2.Plane
import net.vectorworks.motion.math.isCloseTo
import net.vectorworks.motion.math.isRelativelyGreaterThan
import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 9/30/2018.
 *
 * @author kpresnakov
 */

typealias Point = Vector3

fun Point.belongsToLine(line: Line) {

}

fun Point.isInPositiveHalfSpace(plane: Plane) =
    (plane.a * x + plane.b * y + plane.c * z + plane.d).isRelativelyGreaterThan(0.0)

fun Point.belongsToPlane(plane: Plane) =
    (plane.a * x + plane.b * y + plane.c * z + plane.d).isCloseTo(0.0)