package net.vectorworks.motion.time

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

    // Extremely rough and testing-only update mechanism, ensure that periodicTask <= 16.67 ms, else we should have
    // a mechanism for skipping frames
    fun execute(emissionFrequency: Long = 60L, periodicTask: () -> Unit) {
        val intervalInMillis = (1 / emissionFrequency) * 1000L
        periodicTaskDisposable = Flowable.interval(intervalInMillis, java.util.concurrent.TimeUnit.MILLISECONDS)
            .subscribe { periodicTask() }
    }

    fun stop() = periodicTaskDisposable?.dispose()

}