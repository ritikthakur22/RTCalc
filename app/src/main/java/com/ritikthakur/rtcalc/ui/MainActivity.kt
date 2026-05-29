package com.ritikthakur.rtcalc.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import com.ritikthakur.rtcalc.data.repository.ThemeMode
import com.ritikthakur.rtcalc.ui.theme.RTCalcTheme
import com.ritikthakur.rtcalc.ui.view.CalculatorScreen
import com.ritikthakur.rtcalc.ui.viewmodel.CalculatorViewModel
import com.ritikthakur.rtcalc.util.HapticManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

val LocalHapticManager = staticCompositionLocalOf<HapticManager> {
    error("No HapticManager provided")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var hapticManager: HapticManager

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configure edge-to-edge system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val themeMode by viewModel.themeMode.collectAsState()
            val systemDark = isSystemInDarkTheme()
            val isDark = when (themeMode) {
                ThemeMode.SYSTEM -> systemDark
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            CompositionLocalProvider(LocalHapticManager provides hapticManager) {
                RTCalcTheme(darkTheme = isDark) {
                    com.ritikthakur.rtcalc.ui.view.MainScreen(calculatorViewModel = viewModel)
                }
            }
        }
    }
}
