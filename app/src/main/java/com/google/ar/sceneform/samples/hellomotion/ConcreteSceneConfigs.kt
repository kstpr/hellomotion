package com.google.ar.sceneform.samples.hellomotion

import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.math.Vector3

/**
 * Created on 9/22/2018.
 *
 * @author kpresnakov
 */

val defaultBoxSize = Vector3(0.05f, 0.05f, 0.05f)
val defaultWorldSize = Vector3(0.5f, 0.051f, 0.5f)

const val defaultMovableSphereRadius = 0.025f

val simpleScene1 by lazy {
    SceneConfig(
        immovableDatas = listOf(
            ImmovableData(Pose(position = Vector3(0f, 0f, 0f)), collisionShape = createBox(defaultBoxSize)),
            ImmovableData(Pose(position = Vector3(-0.2f, 0f, -0.2f)), collisionShape = createBox(defaultBoxSize)),
            ImmovableData(Pose(position = Vector3(0.2f, 0f, -0.2f)), collisionShape = createBox(defaultBoxSize)),
            ImmovableData(Pose(position = Vector3(-0.2f, 0f, 0.2f)), collisionShape = createBox(defaultBoxSize)),
            ImmovableData(Pose(position = Vector3(0.2f, 0f, 0.2f)), collisionShape = createBox(defaultBoxSize))
        ),
        initialMovablesLocalPoses = listOf(
            Pose(position = Vector3(0.1f, 0f, -0.1f))
        ),
        worldBounds = createBox(defaultWorldSize)
    )
}

fun createBox(size: Vector3) : Box {
    val center = Vector3(0f, size.y / 2, 0f)
    return Box(size, center)
}