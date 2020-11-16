package com.example.fall

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewTreeObserver
import java.lang.Exception

class FlakeView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {

    private var flakeList = ArrayList<Flake>()
    private val paint  = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }
    private var isRunning:Boolean = false
    init {
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        holder.addCallback(this)
        setLayerType(View.LAYER_TYPE_HARDWARE, paint)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

    }

    fun start() {
        this.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                initFlake()
                viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }


    private fun initFlake(){
        flakeList.clear()
        for(i in 0..itemCount) {
            flakeList.add(Flake.create(width, height, paint, 120f))
        }
        isRunning = true
        Thread{
            while (isRunning){
                holder.lockCanvas()?.let { canvas ->
                    try {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                        flakeList.forEach { flake ->
                            flake.draw(canvas, itemText)
                        }
                    }catch (e:Exception){

                    } finally {
                        holder.unlockCanvasAndPost(canvas)
                    }
                }

            }
        }.start()
    }

    fun stop(){
        isRunning = false
    }



    companion object{
        const val itemCount = 30
        const val itemText = "Orz"
    }

}