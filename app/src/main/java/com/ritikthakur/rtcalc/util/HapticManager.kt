package com.ritikthakur.rtcalc.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HapticManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun performKeyPress() {
        android.util.Log.d("RTCALC_HAPTIC", "performKeyPress() called")
        vibrate(30, VibrationEffect.DEFAULT_AMPLITUDE)
    }

    fun performDelete() {
        android.util.Log.d("RTCALC_HAPTIC", "performDelete() called")
        vibrate(50, 150) // slightly stronger and longer
    }

    fun performLongPress() {
        android.util.Log.d("RTCALC_HAPTIC", "performLongPress() called")
        vibrate(70, 200) // noticeable vibration
    }

    fun performError() {
        android.util.Log.d("RTCALC_HAPTIC", "performError() called")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && vibrator?.hasVibrator() == true) {
            val timings = longArrayOf(0, 50, 50, 50)
            val amplitudes = intArrayOf(0, 150, 0, 150)
            android.util.Log.d("RTCALC_HAPTIC", "Vibrator triggered (Error/Waveform)")
            vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(longArrayOf(0, 50, 50, 50), -1)
            android.util.Log.d("RTCALC_HAPTIC", "Vibrator triggered (Error/Legacy)")
        }
    }

    private fun vibrate(duration: Long, amplitude: Int) {
        if (vibrator == null) {
            android.util.Log.d("RTCALC_HAPTIC", "Vibrator is null")
            return
        }
        if (!vibrator.hasVibrator()) {
            android.util.Log.d("RTCALC_HAPTIC", "vibrator.hasVibrator() returned false")
            return
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                android.util.Log.d("RTCALC_HAPTIC", "Vibrator triggered (O+ API)")
                vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
            } else {
                android.util.Log.d("RTCALC_HAPTIC", "Vibrator triggered (Legacy API)")
                @Suppress("DEPRECATION")
                vibrator.vibrate(duration)
            }
        } catch (e: Exception) {
            android.util.Log.e("RTCALC_HAPTIC", "Vibrator failed", e)
        }
    }
}
