package net.vectorworks.motion.time

/**
 * Created on 9/26/2018.
 *
 * DEPENDENT ON JAVA.
 * Abstracts time from the JVM.
 *
 * @author kpresnakov
 */

class TimeProvider(val emissionFrequency: Int) {
    companion object {
        // Rounds down
        fun now(timeUnit: TimeUnit = TimeUnit.NANOSECONDS): Long {
            return when (timeUnit) {
                TimeUnit.NANOSECONDS -> System.nanoTime()
                TimeUnit.MILLISECONDS -> System.nanoTime() / 1_000
                TimeUnit.MICROSECONDS -> System.nanoTime() / 1_000_000
                TimeUnit.SECONDS -> System.nanoTime() / 1_000_000_000
                TimeUnit.MINUTES -> System.nanoTime() / 60_000_000_000
                TimeUnit.HOURS -> System.nanoTime() / 3_600_000_000_000
                TimeUnit.DAYS -> System.nanoTime() / (24 * 3_600_000_000_000)
            }
        }

        fun Long.elapsedTime(timeUnit: TimeUnit = TimeUnit.NANOSECONDS): Long {
            return now(timeUnit) - this
        }
    }
}