package com.google.ar.sceneform.samples.hellomotion.constraints

import com.google.ar.sceneform.Node
import com.google.ar.sceneform.NodeParent
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.samples.hellomotion.motionwrapper.toSceneformVector3
import io.reactivex.Single
import net.vectorworks.motion.constraints.Constraint
import net.vectorworks.motion.constraints.dim1.SegmentConstraint

/**
 * Created on 10/6/2018.
 *
 * @author kpresnakov
 */

class ConstraintNodeFactory() {

    fun createNode(constraint: Constraint, material: Material, parent: NodeParent): Single<Node> {
        return when (constraint) {
            is SegmentConstraint -> makeSegmentNode(
                constraint.start.toSceneformVector3(), constraint.end.toSceneformVector3(), material, parent)
            else -> throw IllegalArgumentException("Unexpected constraint.")
        }
    }

    private fun makeSegmentNode(start: Vector3, end: Vector3, material: Material, parent: NodeParent): Single<Node> {
        val height = Vector3(start.x - end.x, start.y - end.y, start.z - end.z).length()

        return Single.fromCallable {
            ShapeFactory.makeCylinder(0.1f, height, Vector3.zero(), material)
        }.map { modelRenderable ->
            Node().apply {
                renderable = modelRenderable
            }
        }
    }

}
