package com.example.fall

import java.util.Random

/**
 * Created by dingmouren on 2017/4/28.
 */
internal class Random {
    fun getRandom(lower: Float, upper: Float): Float {
        val min = lower.coerceAtMost(upper)
        val max = lower.coerceAtLeast(upper)
        return getRandom(max - min) + min
    }

    fun getRandom(upper: Float): Float {
        return RANDOM.nextFloat() * upper
    }

    fun getRandom(upper: Int): Int {
        return RANDOM.nextInt(upper)
    }

    companion object {
        private val RANDOM = Random()
    }
}