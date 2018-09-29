package net.vectorworks.motion.math

import net.vectorworks.motion.math.linearalgebra.Vector3
import net.vectorworks.motion.math.linearalgebra.areAllElementsEqual
import net.vectorworks.motion.math.linearalgebra.length
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
        asserter.assertTrue("Vector length equals", roundingEquals(unitVector1.length(), 1.0))
    }

    @Test
    fun dot() {
    }

    @Test
    fun times() {
    }

    @Test
    fun minus() {
    }

    @Test
    fun plus() {
    }

    @Test
    fun areAllElementsEqual() {
        asserter.assertTrue("All are equal", listOf(0.02, 0.02, 0.02).areAllElementsEqual())
        asserter.assertTrue("Not all are equal", !listOf(-0.1, 1.2, -0.1).areAllElementsEqual())
        asserter.assertTrue("Not all are equal", !listOf(-0.1, .0, .0).areAllElementsEqual())
        asserter.assertTrue("Not all are equal", !listOf(-0.1, -0.1, 1.2).areAllElementsEqual())
        asserter.assertTrue("All are equal", listOf(-0.13, -0.11, -0.12).areAllElementsEqual(roundingError = 0.1))

    }
}