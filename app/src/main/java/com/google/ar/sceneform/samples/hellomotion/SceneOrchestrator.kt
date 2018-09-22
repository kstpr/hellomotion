package com.google.ar.sceneform.samples.hellomotion

import android.content.Context
import android.graphics.Color
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.NodeParent
import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.collision.CollisionShape
import com.google.ar.sceneform.collision.Sphere
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created on 9/22/2018.
 *
 * @author kpresnakov
 */

typealias SceneformColor = com.google.ar.sceneform.rendering.Color

data class SceneElements(val immovables: List<Node>, val movables: List<Node>)
data class MarkedNode(val immovable: Boolean, val node: Node)

val immovableColor = SceneformColor(Color.BLUE)
val movableColor = SceneformColor(Color.RED)
val worldBoxColor = SceneformColor(Color.argb(10, 0, 0, 0))

// TODO context
class SceneOrchestrator(private val anchorNode: AnchorNode, private val context: Context) {

    fun orchestrateSimpleScene1(): Single<SceneElements> {
        return orchestrateScene(simpleScene1)
    }

    private fun orchestrateScene(sceneConfig: SceneConfig): Single<SceneElements> {

        val immovableNodesCollector = collectImmovableNodes(sceneConfig)
        val movableNodesCollector = collectMovableNodes(sceneConfig)

        val worldBoundsCreator = createWorldBoundsNode()

        return immovableNodesCollector.mergeWith(movableNodesCollector).mergeWith(worldBoundsCreator)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .collectInto(mapOf(true to mutableListOf<Node>(), false to mutableListOf())) { map, markedNode ->
                map[markedNode.immovable]?.add(markedNode.node)
            }
            .map { map ->
                SceneElements(map[true]!!.toList(), map[false]!!.toList())
            }
    }

    private fun createWorldBoundsNode(): Single<MarkedNode> {
        return Single.fromFuture(MaterialFactory.makeTransparentWithColor(context, worldBoxColor))
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { material -> MarkedNode(immovable = true, node = createWorldNode(material, anchorNode)) }
    }

    private fun collectMovableNodes(sceneConfig: SceneConfig): Flowable<MarkedNode> {
        return Single.fromFuture(MaterialFactory.makeOpaqueWithColor(context, movableColor))
            .flatMapPublisher { material: Material ->
                Flowable.fromIterable(sceneConfig.initialMovablesLocalPoses.map { pose -> pose to material })
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { (pose, material) -> createMovableNode(pose, material, anchorNode) }
            .map { MarkedNode(immovable = false, node = it) }
    }

    private fun collectImmovableNodes(sceneConfig: SceneConfig): Flowable<MarkedNode> {
        return Single.fromFuture(MaterialFactory.makeOpaqueWithColor(context, immovableColor))
            .flatMapPublisher { material: Material ->
                Flowable.fromIterable(sceneConfig.immovableDatas.map { data: ImmovableData -> data to material })
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { (data, material) -> createImmovableNode(data, material, anchorNode) }
            .map { MarkedNode(immovable = true, node = it) }
    }

    private fun createWorldNode(material: Material, parent: NodeParent): Node {
        val modelRenderable = ShapeFactory.makeCube(defaultWorldSize, zero, material)
        modelRenderable.isShadowCaster = false
        modelRenderable.isShadowReceiver = false

        return Node().apply{
            renderable = modelRenderable
            setParent(parent)
        }
    }

    private fun createImmovableNode(data: ImmovableData, material: Material, parent: NodeParent): Node {
        val collisionShape = data.collisionShape
        val renderable = createRenderableFromCollisionShape(collisionShape, material)

        return Node().apply {
            setRenderable(renderable)
            setCollisionShape(collisionShape)
            localPosition = data.worldPose.position
            setParent(parent)
        }
    }

    private fun createMovableNode(pose: Pose, material: Material, parent: NodeParent): Node {
        val renderable = ShapeFactory.makeSphere(0.1f, Vector3.zero(), material)

        return Node().apply {
            setRenderable(renderable)
            localPosition = pose.position
            setParent(parent)
        }
    }

    private fun createRenderableFromCollisionShape(collisionShape: CollisionShape, material: Material): ModelRenderable? {
        return when (collisionShape) {
            is Sphere -> ShapeFactory.makeSphere(collisionShape.radius, collisionShape.center, material)
            is Box -> ShapeFactory.makeCube(collisionShape.size, collisionShape.center, material)
            else -> throw IllegalStateException("Collision shape not supported: $collisionShape")
        }
    }
}

