package net.vectorworks.motion.loop

import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

/**
 * Created on 9/26/2018.
 *
 * DEPENDENT ON JAVA & RX
 * Generally emissionFrequency should be the desired fps.
 *
 * @author kpresnakov
 */

class PeriodicExecutor {

    var periodicTaskDisposable: Disposable? = null

    // TODO Extremely rough and testing-only update mechanism, ensure that periodicTask <= 16.67 ms, else we should have
    // TODO a mechanism for skipping frames
    // TODO Or better an interface to hook to the time pulse from Sceneform or other consumers
    fun execute(emissionFrequency: Long = 60L, periodicTask: () -> Unit) {
        val intervalInMillis = ((1.0 / emissionFrequency) * 1_000L).toLong()
        periodicTaskDisposable = Flowable.interval(intervalInMillis, java.util.concurrent.TimeUnit.MILLISECONDS)
            .subscribe { periodicTask() }
    }

    fun stop() = periodicTaskDisposable?.dispose()

}