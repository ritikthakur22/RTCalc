package com.ritikthakur.rtcalc.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ritikthakur.rtcalc.ui.viewmodel.CalculatorViewModel

@Composable
fun SettingsScreenWrapper(
    viewModel: CalculatorViewModel,
    isDark: Boolean,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val themeMode by viewModel.themeMode.collectAsState()
    val angleMode by viewModel.angleMode.collectAsState()
    val decimalPrecision by viewModel.decimalPrecision.collectAsState()
    val hapticEnabled by viewModel.hapticEnabled.collectAsState()
    val scientificNotation by viewModel.scientificNotation.collectAsState()

    // Reusing the existing SettingsScreen but replacing onDismiss with onMenuClick / onBackClick
    // Actually SettingsScreen already uses a full Scaffold. We can just use it.
    // However SettingsScreen expects onDismiss for the back arrow.
    // The user requested a top-left hamburger menu. We'll use the hamburger menu instead of a back button.
    SettingsScreenModified(
        themeMode = themeMode,
        angleMode = angleMode,
        decimalPrecision = decimalPrecision,
        hapticEnabled = hapticEnabled,
        scientificNotation = scientificNotation,
        onThemeChange = { viewModel.setThemeMode(it) },
        onAngleChange = { viewModel.setAngleMode(it) },
        onPrecisionChange = { viewModel.setDecimalPrecision(it) },
        onHapticChange = { viewModel.setHapticEnabled(it) },
        onScientificChange = { viewModel.setScientificNotation(it) },
        onMenuClick = onMenuClick
    )
}
