package net.vectorworks.motion.math.linearalgebra

/**
 * Created on 9/29/2018.
 *
 * Square 2 x 2 matrix of doubles
 *
 * @author kpresnakov
 */

data class Matrix2(val a00: Double, val a01: Double, val a10: Double, val a11: Double)

fun Matrix2.determinant() : Double = a00 * a11 - a01 * a10