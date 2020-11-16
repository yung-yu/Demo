package com.example.fall

import android.graphics.*
import kotlin.math.cos
import kotlin.math.sin

class Flake(
    private val mPoint: Point,
    private var mAngle: Float,
    private val mIncrement: Float,
    private val flakeSize: Float,
    private val  paint: Paint
) {
    fun draw(canvas: Canvas, flake: String) {
        val width = canvas.width
        val height = canvas.height
        paint.textSize = flakeSize
        move(width, height)
        canvas.drawText(flake, mPoint.x.toFloat(), mPoint.y.toFloat(), paint)
    }

    private fun move(width: Int, height: Int) {
        val x = mPoint.x + mIncrement * cos(mAngle.toDouble())
        val y = mPoint.y + mIncrement * sin(mAngle.toDouble())
        mAngle += mRandom.getRandom(
            -ANGLE_SEED,
            ANGLE_SEED
        ) / ANGLE_DIVISOR
        mPoint[x.toInt()] = y.toInt()
        if (!isInside(width, height)) {
            reset(width)
        }
    }

    private fun isInside(width: Int, height: Int): Boolean {
        val x = mPoint.x
        val y = mPoint.y
        return x >= -flakeSize - 1 && x + flakeSize <= width && y >= -flakeSize - 1 && y - flakeSize < height
    }

    private fun reset(width: Int) {
        mPoint.x = mRandom.getRandom(width)
        mPoint.y = (-flakeSize - 1).toInt()
        mAngle =
            mRandom.getRandom(ANGLE_SEED) / ANGLE_SEED * ANGE_RANGE + HALF_PI - HALF_ANGLE_RANGE
    }

    companion object {
        private val TAG = Flake::class.java.name
        private const val ANGE_RANGE = 0.1f
        private const val HALF_ANGLE_RANGE = ANGE_RANGE / 2f
        private const val HALF_PI = Math.PI.toFloat() / 2f
        private const val ANGLE_SEED = 25f
        private const val ANGLE_DIVISOR = 10000f
        private const val INCREMENT_LOWER = 2f
        private const val INCREMENT_UPPER = 4f
        private val mRandom = Random()
        fun create(width: Int, height: Int, paint: Paint, flakeSize: Float): Flake {
            val x = mRandom.getRandom(width)
            val y = mRandom.getRandom(height)
            val positon = Point(x, y)
            val angle = mRandom.getRandom(ANGLE_SEED) / ANGLE_SEED * ANGE_RANGE + HALF_PI - HALF_ANGLE_RANGE
            val increment = mRandom.getRandom(
                INCREMENT_LOWER,
                INCREMENT_UPPER
            )
            return Flake(positon, angle, increment, flakeSize, paint)
        }
    }

}