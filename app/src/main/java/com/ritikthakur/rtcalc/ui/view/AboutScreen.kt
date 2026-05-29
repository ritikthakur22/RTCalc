package com.ritikthakur.rtcalc.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.DarkBackground
import com.ritikthakur.rtcalc.ui.theme.LightBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    isDark: Boolean,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val textColor = MaterialTheme.colorScheme.onBackground

    Scaffold(
        modifier = Modifier.fillMaxSize().background(backgroundColor),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                title = { Text("About", fontWeight = FontWeight.Bold, color = textColor) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "RTCalc",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Developer:\nRitik Thakur",
                fontSize = 18.sp,
                color = textColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Contact:\nritikthakur@duck.com",
                fontSize = 18.sp,
                color = textColor
            )
        }
    }
}
