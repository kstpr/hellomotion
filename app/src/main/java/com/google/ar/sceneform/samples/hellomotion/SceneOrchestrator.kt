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
import com.google.ar.sceneform.samples.hellomotion.constraints.ConstraintNodeFactory
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.vectorworks.motion.constraints.Constraint


/**
 * Created on 9/22/2018.
 *
 * @author kpresnakov
 */

typealias SceneformColor = com.google.ar.sceneform.rendering.Color

data class SceneElements(
    val immovableNodes: List<Node>,
    val movableNodes: List<Pair<Node, Constraint?>>,
    val worldBounds: Box?
)

enum class ElementType {
    MOVABLE,
    IMMOVABLE,
    BOUND_CONSTRAINT,
    GLOBAL_CONSTAINT
}

data class MarkedNode(val elementType: ElementType, val node: Node)

val immovableColor = SceneformColor(Color.BLUE)
val movableColor = SceneformColor(Color.RED)
val constraintColor = SceneformColor(Color.GREEN)
val worldBoxColor = SceneformColor(Color.argb(10, 0, 0, 0))

// TODO context
class SceneOrchestrator(private val anchorNode: AnchorNode, private val context: Context) {

    private val constraintNodeFactory by lazy { ConstraintNodeFactory() }

    fun orchestrateSimpleScene1(): Single<SceneElements> {
        return orchestrateScene(simpleScene1)
    }

    private fun orchestrateScene(sceneConfig: SceneConfig): Single<SceneElements> {

        val immovableNodesCollector = collectImmovableNodes(sceneConfig)
        val movableNodesCollector = collectMovableNodes(sceneConfig)
        val constraintsNodesCollector = collectConstraintNodes(sceneConfig)

        val worldBoundsCreator = createWorldBoundsNode(sceneConfig.worldBounds as Box).toFlowable()

        return Flowable.merge(
            listOf(immovableNodesCollector, movableNodesCollector, constraintsNodesCollector, worldBoundsCreator)
        )
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .collectInto(createEmptySceneElementToNodesMap())
            { map, (elementType, node) ->
                map[elementType]?.add(node)
            }
            .map { map ->
                val constraints = sceneConfig.movableData.map { it.constraint }
                SceneElements(
                    immovableNodes = map[ElementType.IMMOVABLE]!!.toList(),
                    movableNodes = map[ElementType.MOVABLE]!!.toList().zip(constraints),
                    worldBounds = map[ElementType.GLOBAL_CONSTAINT]!!.toList()[0].collisionShape as? Box // HOLEEEY
                )
            }
    }

    private fun createEmptySceneElementToNodesMap(): Map<ElementType, MutableList<Node>> = mapOf(
        ElementType.BOUND_CONSTRAINT to mutableListOf(),
        ElementType.GLOBAL_CONSTAINT to mutableListOf(),
        ElementType.IMMOVABLE to mutableListOf(),
        ElementType.MOVABLE to mutableListOf()
    )

    // TODO - just a global constraint
    private fun createWorldBoundsNode(worldBounds: Box): Single<MarkedNode> {
        return Single.fromFuture(MaterialFactory.makeTransparentWithColor(context, worldBoxColor))
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { material ->
                MarkedNode(
                    elementType = ElementType.GLOBAL_CONSTAINT,
                    node = createWorldNode(worldBounds, material, anchorNode)
                )
            }
    }

    private fun collectMovableNodes(sceneConfig: SceneConfig): Flowable<MarkedNode> {
        return Single.fromFuture(MaterialFactory.makeOpaqueWithColor(context, movableColor))
            .flatMapPublisher { material: Material ->
                Flowable.fromIterable(sceneConfig.movableData.map { data -> data to material })
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { (data, material) -> createMovableNode(data, material, anchorNode) }
            .map { MarkedNode(ElementType.MOVABLE, node = it) }
    }

    private fun collectImmovableNodes(sceneConfig: SceneConfig): Flowable<MarkedNode> {
        return Single.fromFuture(MaterialFactory.makeOpaqueWithColor(context, immovableColor))
            .flatMapPublisher { material: Material ->
                Flowable.fromIterable(sceneConfig.immovableData.map { data: ImmovableData -> data to material })
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { (data, material) -> createImmovableNode(data, material, anchorNode) }
            .map { MarkedNode(elementType = ElementType.IMMOVABLE, node = it) }
    }

    private fun collectConstraintNodes(sceneConfig: SceneConfig): Flowable<MarkedNode> {
        val constraints: List<Constraint> = sceneConfig.movableData
            .asSequence()
            .filter { it.constraint != null }
            .map { it.constraint as Constraint }
            .toList()

        return Single.fromFuture(MaterialFactory.makeOpaqueWithColor(context, constraintColor))
            .flatMapPublisher { material -> Flowable.fromIterable(constraints).map { it to material } }
            .flatMapSingle { (constraint, material) ->
                constraintNodeFactory.createNode(constraint, material, anchorNode)
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { MarkedNode(ElementType.BOUND_CONSTRAINT, it) }
    }

    private fun createWorldNode(worldBounds: Box, material: Material, parent: NodeParent): Node {
        val modelRenderable = ShapeFactory.makeCube(worldBounds.size, worldBounds.center, material)
        modelRenderable.isShadowCaster = false
        modelRenderable.isShadowReceiver = false

        return Node().apply {
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

    private fun createMovableNode(movableData: MovableData, material: Material, parent: NodeParent): Node {
        val renderable = ShapeFactory.makeSphere(
            defaultMovableSphereRadius, Vector3(0f, defaultMovableSphereRadius / 2, 0f), material
        )

        return Node().apply {
            setRenderable(renderable)
            localPosition = movableData.position
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

