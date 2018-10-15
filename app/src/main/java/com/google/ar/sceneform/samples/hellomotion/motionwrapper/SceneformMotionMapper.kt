package com.google.ar.sceneform.samples.hellomotion.motionwrapper

import com.google.ar.sceneform.Node
import net.vectorworks.motion.core.Immovable
import net.vectorworks.motion.core.Movable

/**
 * Created on 9/24/2018.
 *
 * @author kpresnakov
 */

typealias SceneformVector3 = com.google.ar.sceneform.math.Vector3
typealias MotionVector3 = net.vectorworks.motion.math.linearalgebra.Vector3
typealias SceneformCollisionShape = com.google.ar.sceneform.collision.CollisionShape
typealias MotionCollisionShape = net.vectorworks.motion.collision.CollisionShape
typealias SceneformBox = com.google.ar.sceneform.collision.Box
typealias MotionBox = net.vectorworks.motion.collision.Box

fun SceneformVector3.toMotionVector3(): MotionVector3 = MotionVector3(x.toDouble(), y.toDouble(), z.toDouble())

fun MotionVector3.toSceneformVector3(): SceneformVector3 = SceneformVector3(x.toFloat(), y.toFloat(), z.toFloat())

fun SceneformCollisionShape.toMotionCollisionShape() : MotionCollisionShape {
    return if (this is SceneformBox) {
        MotionBox(center = this.center.toMotionVector3(), size = this.size.toMotionVector3())
    } else MotionBox()
}

internal fun Node.toMovable(id: Long): Movable {
    return Movable(id, localPosition.toMotionVector3(), collisionShape.toMotionCollisionShape())
}

internal fun Node.toImmovable(id: Long): Immovable {
    return Immovable(id, localPosition.toMotionVector3(), collisionShape.toMotionCollisionShape())
}
