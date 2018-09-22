package com.google.ar.sceneform.samples.hellomotion

import com.google.ar.sceneform.collision.CollisionShape
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3

/**
 * Created on 9/22/2018.
 *
 * Helper for fast switching of different scene configurations
 * @author kpresnakov
 */

data class Pose(val position: Vector3, val rotation: Quaternion = Quaternion())

data class ImmovableData(val worldPose: Pose, val collisionShape: CollisionShape)

data class SceneConfig(val immovableDatas: List<ImmovableData>,
                       val initialMovablesLocalPoses: List<Pose>,
                       val worldBounds: CollisionShape)