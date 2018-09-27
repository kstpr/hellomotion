/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.ar.sceneform.samples.hellomotion

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment



class HelloSceneformActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var sceneCreated = false

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }

        setContentView(R.layout.activity_ux)
        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as? ArFragment ?:
            throw IllegalStateException("No business without a fragment, buddy!")

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            if (sceneCreated) return@setOnTapArPlaneListener

            val newAnchor = hitResult.createAnchor()
            val anchorNode = AnchorNode().apply {
                anchor = newAnchor
                setParent(arFragment.arSceneView.scene)
            }

            sceneCreated = true
            SceneOrchestrator(anchorNode, this)
                .orchestrateSimpleScene1()
                .subscribe()
        }
    }

    companion object {
        private val TAG = HelloSceneformActivity::class.java.simpleName
        private const val MIN_OPENGL_VERSION = 3.0

        fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
            if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
                Log.e(TAG, "Sceneform requires Android N or later")
                Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show()
                activity.finish()
                return false
            }
            val openGlVersionString = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                    .deviceConfigurationInfo
                    .glEsVersion
            if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
                Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
                Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                        .show()
                activity.finish()
                return false
            }
            return true
        }
    }
}
