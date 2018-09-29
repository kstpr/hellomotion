package net.vectorworks.motion.math.geometry.objects.dim2

import net.vectorworks.motion.math.linearalgebra.Vector3
import org.junit.Test
import kotlin.test.asserter

/**
 * Created on 9/29/2018.
 *
 * @author kpresnakov
 */
class PlaneTest {

    @Test
    fun getNormal() {
        val definedBy3Points = Plane.fromThreePoints(
            Vector3(1.0, .0, .0),
            Vector3(.0, .0, 1.0),
            Vector3(0.5, .0, 0.5)
        )
        val normalsAreLinearDependent =
            Vector3.testForLinearDependence(definedBy3Points.normal, Vector3(.0, 1.0, .0))
        asserter.assertTrue("Normals are proper", normalsAreLinearDependent)
    }

    @Test
    fun distanceTo() {
    }

}