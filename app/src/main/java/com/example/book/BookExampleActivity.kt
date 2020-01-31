package com.example.book

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.example.R
import com.example.book.view.TurnBook
import kotlinx.android.synthetic.main.activtiy_book.*

class BookExampleActivity : Activity() {
    companion object {
        const val TAG = "BookExampleActivity"
        const val PREFERENCE_BOOK_MARK = "BOOK_MARK"
    }
    private var bookView:TurnBook? = null
    private val bookPreference:SharedPreferences by lazy {
        getSharedPreferences("book", Context.MODE_PRIVATE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activtiy_book)
        container?.doOnPreDraw {
            bookView = TurnBook(
                container?.context!!,
                container?.width ?: 0,
                container?.height ?: 0,
                16,
                16
            )
            container?.addView(
                bookView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            bookView?.setBookFile("book.txt", true)
            bookView?.ToBookMarkPage(bookPreference.getInt(PREFERENCE_BOOK_MARK, 0))
        }
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
        bookPreference.edit()
            .putInt(PREFERENCE_BOOK_MARK,
                bookView?.bookPageFactory?.m_mbBufBegin?:0)
            .apply()
        bookView?.recycle()
    }
}