package com.google.ar.sceneform.samples.hellomotion

import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.math.Vector3

/**
 * Created on 9/22/2018.
 *
 * @author kpresnakov
 */
val zero: Vector3 = Vector3.zero()
val defaultBoxSize = Vector3(0.1f, 0.1f, 0.1f)
val defaultWorldSize = Vector3(2.5f, 0.2f, 2.5f)

val simpleScene1 = SceneConfig(
    immovableDatas = listOf(
        ImmovableData(Pose(position = Vector3(0f, 0f, 0f)), collisionShape = Box(defaultBoxSize, zero)),
        ImmovableData(Pose(position = Vector3(-0.5f, 0f, -0.5f)), collisionShape = Box(defaultBoxSize, zero))
    ),
    initialMovablesLocalPoses = listOf(
        Pose(position = Vector3(0.5f, 0f, -0.5f))
    ),
    worldBounds = Box(Vector3.zero(), defaultWorldSize)
)