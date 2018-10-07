package com.google.ar.sceneform.samples.hellomotion.motionwrapper

import com.google.ar.sceneform.Scene
import net.vectorworks.motion.collision.Collider
import net.vectorworks.motion.collision.HitTestResult
import net.vectorworks.motion.collision.Ray
import net.vectorworks.motion.core.RigidBody

/**
 * Created on 9/23/2018.
 *
 * @author kpresnakov
 */
class SceneformCollider(val scene: Scene) : Collider {
    override fun hitTest(ray: Ray): HitTestResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hitTestAll(ray: Ray): List<HitTestResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hitTestPlane(ray: Ray): HitTestResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hitTestWorldBounds(ray: Ray): HitTestResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun overlapTest(rigidBody: RigidBody): RigidBody {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun overlapTestAll(rigidBody: RigidBody): List<RigidBody> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun overlapTestPlane(rigidBody: RigidBody): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun overlapTestWorldBounds(rigidBody: RigidBody): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}