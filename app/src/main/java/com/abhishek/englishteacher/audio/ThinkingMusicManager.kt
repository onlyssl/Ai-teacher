package com.abhishek.englishteacher.audio

import android.content.Context
import android.media.MediaPlayer
import com.abhishek.englishteacher.R

class ThinkingMusicManager(
    private val context: Context
) {

    private var player: MediaPlayer? = null

    fun start() {

        if (player == null) {

            player = MediaPlayer.create(
                context,
                R.raw.thinking_music
            )

            player?.isLooping = true

        }

        player?.start()

    }

    fun stop() {

        player?.stop()
        player?.release()
        player = null

    }

}