package com.example.biometric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import kotlinx.android.synthetic.main.activity_biometric.*
import android.os.Looper

import android.os.Handler
import com.example.R
import java.util.concurrent.Executor


class BiometricDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("title")
            .setSubtitle("subTitle")
            .setNegativeButtonText("ok")
            .build()

        val mAuthenticationCallback = object: BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@BiometricDemoActivity, errString, Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@BiometricDemoActivity, "Succeeded", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@BiometricDemoActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
        }

        val biometricPrompt = BiometricPrompt(this, UiThreadExecutor(), mAuthenticationCallback)

        auth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }
     class UiThreadExecutor : Executor {
        override fun execute(command: Runnable) {
            mHandler.post(command)
        }

        private val mHandler = Handler(Looper.getMainLooper())
    }
}
