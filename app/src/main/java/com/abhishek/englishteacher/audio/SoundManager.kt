package com.abhishek.englishteacher.audio

import android.content.Context
import android.media.MediaPlayer
import com.abhishek.englishteacher.R

class SoundManager(
    private val context: Context
) {

    fun playReceivedSound() {

        val player = MediaPlayer.create(
            context,
            R.raw.ai_received
        )

        player.setOnCompletionListener {
            it.release()
        }

        player.start()

    }

}