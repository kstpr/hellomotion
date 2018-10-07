package net.vectorworks.motion.math.geometry.objects.dim1

import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 10/6/2018.
 *
 * @author kpresnakov
 */
data class Rectangle(val topLeft: Point, val topRight: Point, val bottomLeft: Point, val bottomRight: Point) {
    companion object {
        fun fromCenterAndDimensions(center: Point, normal: Vector3, width: Double, height: Double): Rectangle {
            return TODO()
        }
    }
}