package net.vectorworks.motion.math.geometry.objects.dim1

import net.vectorworks.motion.math.geometry.objects.dim0.Point
import net.vectorworks.motion.math.linearalgebra.Vector3
import org.junit.Test
import kotlin.test.asserter

/**
 * Created on 10/6/2018.
 *
 * @author kpresnakov
 */
class LineKtTest {

    @Test
    fun isBelongingToALine() {
        val line = Line(Point.zero(), Vector3(1.0, 1.0, 1.0))

        val point = Point(0.5, 0.5, 0.5)
        asserter.assertTrue("Point belongs to a line", point.isBelongingToALine(line))

        val point2 = Point(0.0, 0.4, 0.3)
        asserter.assertTrue("Point belongs to a line", !point2.isBelongingToALine(line))
    }
}