package com.google.ar.sceneform.samples.hellomotion

import com.google.ar.sceneform.collision.CollisionShape
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import net.vectorworks.motion.constraints.Constraint

/**
 * Created on 9/22/2018.
 *
 * Helper for fast switching of different scene configurations
 * @author kpresnakov
 */

data class MovableData(
    val position: Vector3,
    val rotation: Quaternion = Quaternion(),
    val constraint: Constraint? = null
)

data class ImmovableData(
    val worldPose: MovableData,
    val collisionShape: CollisionShape
)

data class SceneConfig(
    val immovableData: List<ImmovableData>,
    val movableData: List<MovableData>,
    val worldBounds: CollisionShape
)