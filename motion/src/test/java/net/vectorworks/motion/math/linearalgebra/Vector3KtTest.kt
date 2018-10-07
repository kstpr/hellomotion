package net.vectorworks.motion.math.linearalgebra

import net.vectorworks.motion.math.areAllElementsEqual
import net.vectorworks.motion.math.isCloseTo
import org.junit.Test
import kotlin.math.sqrt
import kotlin.test.asserter

/**
 * Created on 9/30/2018.
 *
 * @author kpresnakov
 */
class Vector3KtTest {

    @Test
    fun length() {
        val zeroVector = Vector3.zero()
        asserter.assertEquals("Vector length equals", zeroVector.length(), 0.0)
        val unitVector = Vector3(0.0, 1.0, 0.0)
        asserter.assertEquals("Vector length equals", unitVector.length(), 1.0)

        val unitVector1 = Vector3(1 / sqrt(2.0), 1 / sqrt(2.0), 0.0)
        asserter.assertTrue("Vector length equals", unitVector1.length().isCloseTo(1.0))
    }

    @Test
    fun dot() {
    }

    @Test
    fun times() {
        val left = Vector3(1.0, 0.0, 0.0)
        val right = Vector3(0.0, 1.0, 0.0)
        val crossProduct = left x right


    }

    @Test
    fun minus() {
    }

    @Test
    fun plus() {
        val left = Vector3(3.0, 4.0, 5.0)
        val right = Vector3(2.0, 1.0, 0.0)
        val expected = Vector3(5.0, 5.0, 5.0)
        asserter.assertTrue("Sum is not correct", (left + right).equalsWithinToleranceTo(expected))

        val left1 = Vector3.zero()
        val right1 = Vector3(2.0, 1.0, 0.0)
        val expected1 = right1
        asserter.assertTrue("Sum is not correct", (left1 + right1).equalsWithinToleranceTo(expected1))
    }

    @Test
    fun areAllElementsEqual() {
        asserter.assertTrue("All are equal fail", listOf(0.02, 0.02, 0.02).areAllElementsEqual())
        asserter.assertTrue("Not all are equal fail", !listOf(-0.1, 1.2, -0.1).areAllElementsEqual())
        asserter.assertTrue("Not all are equal fail", !listOf(-0.1, .0, .0).areAllElementsEqual())
        asserter.assertTrue("Not all are equal fail", !listOf(-0.1, -0.1, 1.2).areAllElementsEqual())
        asserter.assertTrue("All are equal fail",
            listOf(-0.13, -0.11, -0.12).areAllElementsEqual(roundingError = 0.1))
    }

    @Test
    fun normalized() {
        val vector = Vector3(1.0,1.0,1.0)
        val coord = 1.0 / sqrt(3.0)
        val expected = Vector3(coord, coord, coord)
        asserter.assertTrue("Normalized", vector.normalized().equalsWithinToleranceTo(expected))

        val vector2 = Vector3.zero()
        val expected2 = Vector3.zero()
        asserter.assertTrue("Normalized", vector2.normalized().equalsWithinToleranceTo(expected2))

        val vector3 = Vector3(5.7, 0.0, 0.0)
        val expected3 = Vector3(1.0, 0.0, 0.0)
        asserter.assertTrue("Normalized", vector3.normalized().equalsWithinToleranceTo(expected3))
    }
}