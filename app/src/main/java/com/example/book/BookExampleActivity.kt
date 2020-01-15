package com.example.book

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.R
import com.example.book.view.TurnBook
import kotlinx.android.synthetic.main.activtiy_book.*

class BookExampleActivity : Activity() {
    companion object {
        const val TAG = "BookExampleActivity"
    }
    private var bookView:TurnBook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activtiy_book)
        container?.viewTreeObserver?.addOnGlobalLayoutListener(
            object:ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    bookView = TurnBook(container?.context!!, container?.width?:0, container?.height?:0, 16, 16)
                    container?.addView(bookView,
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                    bookView?.setBookFile("book.txt", true)
                    bookView?.NextPage(false)
                    container?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}