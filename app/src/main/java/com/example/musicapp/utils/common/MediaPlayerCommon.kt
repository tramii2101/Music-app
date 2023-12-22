package com.example.musicapp.utils.common

import android.media.AudioAttributes
import android.media.MediaPlayer

object MediaPlayerCommon {
    fun createMediaPlayer(): MediaPlayer {
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )
        }

        return mediaPlayer
    }

}