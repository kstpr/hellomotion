package net.vectorworks.motion.math.geometry

import net.vectorworks.motion.math.geometry.objects.dim0.Point

/**
 * Created on 10/6/2018.
 *
 * @author kpresnakov
 */

fun Point.getCollinearityQuotients(point1: Point, point2: Point): Triple<Double, Double, Double> {
    val quotient = { r: Double, r1: Double, r2: Double -> (r - r1) / (r2 - r1) }

    val quotientX = quotient(this.x, point1.x, point2.x)
    val quotientY = quotient(this.y, point1.y, point2.y)
    val quotientZ = quotient(this.z, point1.z, point2.z)
    return kotlin.Triple(quotientX, quotientY, quotientZ)
}