package com.example.kirton_song

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class LyricsActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics)

        val title  = intent.getStringExtra("title")  ?: ""
        val lyrics = intent.getStringExtra("lyrics") ?: ""

        val toolbar = findViewById<MaterialToolbar>(R.id.topBarLyrics)
        toolbar.title = title
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Title types out first
        val songTitleText = findViewById<TextView>(R.id.songTitleText)
        val lyricsText    = findViewById<TextView>(R.id.lyricsText)

        typeText(
            text     = title,
            textView = songTitleText,
            delayMs  = 60L,           // faster for short title
            onDone   = {
                // Lyrics start typing after title finishes
                typeText(
                    text     = lyrics,
                    textView = lyricsText,
                    delayMs  = 20L    // faster for long lyrics
                )
            }
        )
    }

    private fun typeText(
        text: String,
        textView: TextView,
        delayMs: Long = 30L,
        onDone: (() -> Unit)? = null
    ) {
        var index = 0
        val runnable = object : Runnable {
            override fun run() {
                if (index <= text.length) {
                    textView.text = text.substring(0, index)
                    index++
                    handler.postDelayed(this, delayMs)
                } else {
                    onDone?.invoke()
                }
            }
        }
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}