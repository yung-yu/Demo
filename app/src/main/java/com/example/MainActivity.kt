package com.example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.di.component.Student
import com.example.biometric.BiometricDemoActivity
import com.example.book.BookExampleActivity
import com.example.vision.QrCodeVisionDemoActivity
import com.example.vision.TextVisionDemoActivity
import com.example.vision.ZxingQrCodeVisionDemoActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    private  lateinit var behavior:BottomSheetBehavior<View>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        toolbar?.setNavigationOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 100
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isFitToContents = false
        behavior.setBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(p0: View, p1: Float) {
                if(behavior.state == BottomSheetBehavior.STATE_SETTLING) {
                    if (p1 < 0.7 && p1 > 0.3) {
                        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    }
                }
            }

            override fun onStateChanged(p0: View, p1: Int) {

            }

        })

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val data = ArrayList<String>()
        for (i in 0..100){
            data.add("item$i")
        }
        recyclerView.adapter = StringAdapter(this, data)
        val student = Student()
        Log.d(TAG, "student scroe ${student.test.score}")


    }

    override fun onBackPressed() {
        if(behavior.state == BottomSheetBehavior.STATE_EXPANDED){
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            return
        } else {
            super.onBackPressed()
        }

    }

    fun openBiometric(view: View){
        startActivity(Intent(this, BiometricDemoActivity::class.java))

    }

    fun openQrCodeVision(view: View){
        startActivity(Intent(this, QrCodeVisionDemoActivity::class.java))
    }

    fun openTextVision(view:View){
        startActivity(Intent(this, TextVisionDemoActivity::class.java))
    }

    fun openQrCodeZxing(view:View){
        startActivity(Intent(this, ZxingQrCodeVisionDemoActivity::class.java))
    }
    fun openBook(view:View){
        startActivity(Intent(this, BookExampleActivity::class.java))
    }

    class StringAdapter(val context: Context, var data:ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        class ItemViewHolder(var view:View):RecyclerView.ViewHolder(view){

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
           return ItemViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false))
        }

        override fun getItemCount(): Int {
           return data.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is ItemViewHolder){
                val textView = holder.itemView as TextView
                textView.text = data[position]
            }
        }

    }
}