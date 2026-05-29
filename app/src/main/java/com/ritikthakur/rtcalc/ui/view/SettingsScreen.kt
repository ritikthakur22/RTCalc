package com.ritikthakur.rtcalc.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ritikthakur.rtcalc.data.repository.AngleMode
import com.ritikthakur.rtcalc.data.repository.ThemeMode
import com.ritikthakur.rtcalc.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    angleMode: AngleMode,
    decimalPrecision: Int,
    hapticEnabled: Boolean,
    scientificNotation: Boolean,
    onThemeChange: (ThemeMode) -> Unit,
    onAngleChange: (AngleMode) -> Unit,
    onPrecisionChange: (Int) -> Unit,
    onHapticChange: (Boolean) -> Unit,
    onScientificChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var activeSubScreen by remember { mutableStateOf<SettingsSubScreen?>(null) }

    if (activeSubScreen != null) {
        SubScreenDialog(
            subScreen = activeSubScreen!!,
            onDismiss = { activeSubScreen = null }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Theme Mode Preference
            SettingsCard(title = "App Theme") {
                Column(Modifier.selectableGroup()) {
                    ThemeMode.values().forEach { mode ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .selectable(
                                    selected = (themeMode == mode),
                                    onClick = { onThemeChange(mode) }
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (themeMode == mode),
                                onClick = null,
                                colors = RadioButtonDefaults.colors(selectedColor = Orange)
                            )
                            Text(
                                text = when (mode) {
                                    ThemeMode.SYSTEM -> "System Default"
                                    ThemeMode.LIGHT -> "Light Theme"
                                    ThemeMode.DARK -> "Dark Theme"
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }

            // Angle Mode Preference
            SettingsCard(title = "Trigonometry Angle Mode") {
                Column(Modifier.selectableGroup()) {
                    AngleMode.values().forEach { mode ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .selectable(
                                    selected = (angleMode == mode),
                                    onClick = { onAngleChange(mode) }
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (angleMode == mode),
                                onClick = null,
                                colors = RadioButtonDefaults.colors(selectedColor = Orange)
                            )
                            Text(
                                text = when (mode) {
                                    AngleMode.DEGREE -> "Degrees (°)"
                                    AngleMode.RADIAN -> "Radians (rad)"
                                    AngleMode.GRADIAN -> "Gradians (grad)"
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }

            // Decimal Precision Preference
            SettingsCard(title = "Decimal Precision") {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Display Decimals: $decimalPrecision", fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = decimalPrecision.toFloat(),
                        onValueChange = { onPrecisionChange(it.toInt()) },
                        valueRange = 2f..14f,
                        steps = 11,
                        colors = SliderDefaults.colors(
                            thumbColor = Orange,
                            activeTrackColor = Orange
                        )
                    )
                }
            }

            // Toggles
            SettingsCard(title = "Preferences") {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Haptic Feedback", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                            Text("Vibrate keys on tap", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = hapticEnabled,
                            onCheckedChange = onHapticChange,
                            colors = SwitchDefaults.colors(checkedThumbColor = Orange, checkedTrackColor = Orange.copy(alpha = 0.5f))
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Scientific Notation", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                            Text("Force exponential outputs", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = scientificNotation,
                            onCheckedChange = onScientificChange,
                            colors = SwitchDefaults.colors(checkedThumbColor = Orange, checkedTrackColor = Orange.copy(alpha = 0.5f))
                        )
                    }
                }
            }

            // Play Store Metadata Pages Links
            SettingsCard(title = "Information") {
                Column {
                    ListItem(
                        headlineContent = { Text("About RTCalc") },
                        leadingContent = { Icon(Icons.Default.Info, contentDescription = null) },
                        modifier = Modifier.clickable { activeSubScreen = SettingsSubScreen.ABOUT }
                    )
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    ListItem(
                        headlineContent = { Text("Privacy Policy") },
                        leadingContent = { Icon(Icons.Default.Lock, contentDescription = null) },
                        modifier = Modifier.clickable { activeSubScreen = SettingsSubScreen.PRIVACY_POLICY }
                    )
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    ListItem(
                        headlineContent = { Text("Open Source Licenses") },
                        leadingContent = { Icon(Icons.Default.Info, contentDescription = null) },
                        modifier = Modifier.clickable { activeSubScreen = SettingsSubScreen.LICENSES }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsCard(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Orange,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            content()
        }
    }
}

enum class SettingsSubScreen {
    ABOUT, PRIVACY_POLICY, LICENSES
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubScreenDialog(
    subScreen: SettingsSubScreen,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = when (subScreen) {
                                SettingsSubScreen.ABOUT -> "About App"
                                SettingsSubScreen.PRIVACY_POLICY -> "Privacy Policy"
                                SettingsSubScreen.LICENSES -> "Open Source Licenses"
                            },
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    when (subScreen) {
                        SettingsSubScreen.ABOUT -> {
                            Text("RTCalc", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Orange)
                            Text("Version: 1.0.0", fontSize = 16.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "RTCalc is a professional high-precision calculator application for Android. Styled with an elegant, iPhone-inspired interface and featuring a complete scientific math execution layout, RTCalc makes calculations of any length simple and precise.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Developer: Ritik Thakur", fontWeight = FontWeight.Bold)
                            Text("Contact: support@ritikthakur.com")
                        }
                        SettingsSubScreen.PRIVACY_POLICY -> {
                            Text("Privacy Policy", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Orange)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "RTCalc performs all mathematical calculations locally on your device. We do not collect, capture, store, transmit, or share any personal information, location coordinates, calculations data, or usage logs.\n\n" +
                                "Calculation history is saved locally in a secure sandbox SQLite database. You can wipe this local calculation database at any time from the history panel.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        SettingsSubScreen.LICENSES -> {
                            Text("Open Source Licences", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Orange)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "RTCalc relies on standard open-source Android libraries. License summaries are provided below:\n\n" +
                                "1. Jetpack Compose:\n" +
                                "   Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0)\n\n" +
                                "2. Dagger-Hilt DI:\n" +
                                "   Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0)\n\n" +
                                "3. Room Database Storage:\n" +
                                "   Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0)\n\n" +
                                "4. Jetpack DataStore Preferences:\n" +
                                "   Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
