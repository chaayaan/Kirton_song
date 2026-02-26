package com.example.kirton_song

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private var charIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val titleText     = findViewById<TextView>(R.id.splashTitle)
        val copyrightText = findViewById<TextView>(R.id.textView)
        val appName       = "আড়াই দিনের কীর্তনের গান"

        // Hide copyright at start
        copyrightText.alpha        = 0f
        copyrightText.scaleX       = 0.7f
        copyrightText.scaleY       = 0.7f
        copyrightText.translationY = 30f

        // Typing effect
        val typingRunnable = object : Runnable {
            override fun run() {
                if (charIndex <= appName.length) {
                    titleText.text = appName.substring(0, charIndex)
                    charIndex++
                    handler.postDelayed(this, 100)
                } else {
                    // Typing done → pop up the copyright branding
                    popUpCopyright(copyrightText)

                    // Then navigate to MainActivity
                    handler.postDelayed({
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }, 1800) // 1.8s after typing finishes
                }
            }
        }

        handler.post(typingRunnable)
    }

    private fun popUpCopyright(view: TextView) {
        val fadeIn  = ObjectAnimator.ofFloat(view, "alpha",        0f,   1f)
        val scaleX  = ObjectAnimator.ofFloat(view, "scaleX",       0.7f, 1f)
        val scaleY  = ObjectAnimator.ofFloat(view, "scaleY",       0.7f, 1f)
        val slideUp = ObjectAnimator.ofFloat(view, "translationY", 30f,  0f)

        AnimatorSet().apply {
            playTogether(fadeIn, scaleX, scaleY, slideUp)
            duration     = 500                        // 500ms pop animation
            interpolator = OvershootInterpolator(2f)  // bouncy finish
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // no memory leaks
    }
}