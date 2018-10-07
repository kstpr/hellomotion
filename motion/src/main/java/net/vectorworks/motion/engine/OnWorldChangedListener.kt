package net.vectorworks.motion.engine

import net.vectorworks.motion.math.linearalgebra.Vector3

/**
 * Created on 10/6/2018.
 *
 * @author kpresnakov
 */
interface OnWorldChangedListener {
    fun onWorldChanged(movablesData: List<Pair<Long, Vector3>>)
}