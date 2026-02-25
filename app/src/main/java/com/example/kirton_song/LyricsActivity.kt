package com.example.kirton_song

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class LyricsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics)

        val title = intent.getStringExtra("title")
        val lyrics = intent.getStringExtra("lyrics")

        val songTitleText = findViewById<TextView>(R.id.songTitleText)
        songTitleText.text = title

        val toolbar = findViewById<MaterialToolbar>(R.id.topBarLyrics)
        toolbar.title = title
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val lyricsText = findViewById<TextView>(R.id.lyricsText)
        lyricsText.text = lyrics
    }
}