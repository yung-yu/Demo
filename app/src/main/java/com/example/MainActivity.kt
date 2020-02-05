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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biometric.BiometricDemoActivity
import com.example.book.BookExampleActivity
import com.example.vision.QrCodeVisionDemoActivity
import com.example.vision.TextVisionDemoActivity
import com.example.vision.ZxingQrCodeVisionDemoActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 100
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isFitToContents = false
        recyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<String>()
        for (i in 0..100){
            data.add("item$i")
        }
        recyclerView.adapter = StringAdapter(this, data)
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