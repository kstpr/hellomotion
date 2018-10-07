package net.vectorworks.motion.math.geometry.objects.dim1

import net.vectorworks.motion.math.geometry.getCollinearityQuotients
import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.isCloseTo
import net.vectorworks.motion.math.isInRelativeRange

/**
 * Created on 9/26/2018.
 *
 * @author kpresnakov
 */
open class Segment(val start: Point, val end: Point) {
    override fun toString(): String {
        return "Segment [$start, $end]"
    }
}

fun Point.isBelongingToASegment(segment: Segment): Boolean {
    val (quotientX, quotientY, quotientZ) = getCollinearityQuotients(segment.start, segment.end)

    val isPointOnLine =
        quotientX.isCloseTo(quotientY) && quotientX.isCloseTo(quotientZ) && quotientY.isCloseTo(quotientZ)
    return if (isPointOnLine) quotientX.isInRelativeRange(0.0, 1.0) else false
}
