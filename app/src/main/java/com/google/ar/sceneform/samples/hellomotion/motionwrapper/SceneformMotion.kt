package com.google.ar.sceneform.samples.hellomotion.motionwrapper

import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.collision.Box
import net.vectorworks.motion.core.Immovable
import net.vectorworks.motion.core.Movable
import net.vectorworks.motion.core.RigidBody
import net.vectorworks.motion.engine.Motion
import net.vectorworks.motion.engine.OnWorldChangedListener

/**
 * Created on 9/23/2018.
 *
 * Wraps motion to be used with Sceneform
 * @author kpresnakov
 */

class SceneformMotion(private val scene: Scene) : OnWorldChangedListener {
    private val collider by lazy { SceneformCollider(scene) }
    private val motion by lazy { Motion(collider) }

    private val idOfMovableToNode = mutableMapOf<Long, Node>()
    private val idOfImmovableToNode = mutableMapOf<Long, Node>()

    fun initialize(movableNodes: List<Node>, immovableNodes: List<Node>, worldBounds: Box? = null) {
        val initialMovables =
            indexAndRegisterRigidBodies(movableNodes, idOfMovableToNode, Node::toMovable).map { it as Movable }

        val initialImmovables =
            indexAndRegisterRigidBodies(immovableNodes, idOfImmovableToNode, Node::toImmovable).map { it as Immovable }

        motion.initialize(initialMovables, initialImmovables, worldBounds?.toMotionCollisionShape())
    }

    fun start() = motion.start(this)

    fun registerMovable(movableNode: Node) {
        // TODO maybe not efficient
        val index = (idOfMovableToNode.keys.max() ?: 0) + 1
        idOfMovableToNode[index] = movableNode

        motion.registerMovable(movableNode.toMovable(id = index))
    }

    fun registerImmovable(immovableNode: Node) {
        val index = (idOfImmovableToNode.keys.max() ?: 0) + 1
        idOfImmovableToNode[index] = immovableNode

        motion.registerImmovable(immovableNode.toImmovable(id = index))
    }

    override fun onWorldChanged(movablesData: List<Pair<Long, MotionVector3>>) {
        movablesData.forEach { (id, position) ->
            idOfMovableToNode[id]?.localPosition = position.toSceneformVector3()
        }
    }

    private fun indexAndRegisterRigidBodies(nodes: List<Node>, indexToNodeMap: MutableMap<Long, Node>,
                                            createRigidBody: Node.(Long) -> RigidBody): List<RigidBody> {
        val nodesWithIndex = indexNodes(nodes)
        indexToNodeMap.putAll(nodesWithIndex.toMap())

        return nodesWithIndex
            .map { (index, node) -> node.createRigidBody(index) }
    }

    private fun indexNodes(nodes: List<Node>): List<Pair<Long, Node>> = nodes
        .asSequence()
        .mapIndexed { index, node -> index.toLong() to node }
        .toList()

}
