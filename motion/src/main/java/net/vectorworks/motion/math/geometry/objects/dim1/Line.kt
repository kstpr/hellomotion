package net.vectorworks.motion.math.geometry.objects.dim1

import net.vectorworks.motion.math.geometry.getCollinearityQuotients
import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.isCloseTo
import net.vectorworks.motion.math.linearalgebra.Vector3
import net.vectorworks.motion.math.linearalgebra.minus
import net.vectorworks.motion.math.linearalgebra.plus

/**
 * Created on 9/26/2018.
 *
 * @author kpresnakov
 */
class Line(val point: Point, val direction: Vector3) {
    companion object {
        fun fromTwoPoints(point1: Point, point2: Point): Line {
            if (point1.equalsWithinToleranceTo(point2))
                throw IllegalArgumentException("Cannot create a line from two equal points: $point1 and $point2")

            return Line(point1, point2 - point1)
        }
    }

    override fun toString(): String {
        return "Line [point: $point, direction: $direction]"
    }
}

fun Point.isBelongingToALine(line: Line): Boolean {
    val point1 = line.point
    val point2 = line.point + line.direction

    val (quotientX, quotientY, quotientZ) = getCollinearityQuotients(point1, point2)

    return quotientX.isCloseTo(quotientY) && quotientX.isCloseTo(quotientZ) && quotientY.isCloseTo(quotientZ)
}