package com.ritikthakur.rtcalc.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.data.repository.ThemeMode
import com.ritikthakur.rtcalc.ui.theme.DarkBackground
import com.ritikthakur.rtcalc.ui.theme.LightBackground
import com.ritikthakur.rtcalc.ui.theme.Orange
import com.ritikthakur.rtcalc.ui.viewmodel.CalculatorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    
    // UI state flows
    val expression by viewModel.expression.collectAsState()
    val displayValue by viewModel.displayValue.collectAsState()
    val historyList by viewModel.historyList.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val hapticEnabled by viewModel.hapticEnabled.collectAsState()

    // Sheet and settings dropdown states
    var showHistorySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showMenu by remember { mutableStateOf(false) }

    // Screen classification: Tablet vs Phone (600dp boundary)
    val isTablet = configuration.screenWidthDp >= 600
    val systemDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val backgroundColor = if (isDark) DarkBackground else LightBackground

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RTCalc",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    // Show history shortcut only on mobile (since history is side-by-side on tablet)
                    if (!isTablet) {
                        IconButton(onClick = { showHistorySheet = true }) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "History",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    // Settings dropdown icon
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Light Mode") },
                                onClick = {
                                    viewModel.setThemeMode(ThemeMode.LIGHT)
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Dark Mode") },
                                onClick = {
                                    viewModel.setThemeMode(ThemeMode.DARK)
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("System Theme") },
                                onClick = {
                                    viewModel.setThemeMode(ThemeMode.SYSTEM)
                                    showMenu = false
                                }
                            )
                            Divider()
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = if (hapticEnabled) "Disable Haptic" else "Enable Haptic"
                                    )
                                },
                                onClick = {
                                    viewModel.setHapticEnabled(!hapticEnabled)
                                    showMenu = false
                                }
                            )
                        }
                    }
                },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { innerPadding ->
        if (isTablet) {
            // Tablet Layout: Split screen side-by-side (History left, Calculator right)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Persistent History Panel (Left side)
                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxHeight()
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = context.getString(com.ritikthakur.rtcalc.R.string.title_history),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (historyList.isNotEmpty()) {
                            Text(
                                text = context.getString(com.ritikthakur.rtcalc.R.string.clear_history),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Orange,
                                modifier = Modifier.clickable {
                                    viewModel.clearAllHistory()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(8.dp))

                    if (historyList.isEmpty()) {
                        BoxEmptyHistory(modifier = Modifier.weight(1f))
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(historyList, key = { it.id }) { item ->
                                HistoryRowItem(
                                    item = item,
                                    onClick = { viewModel.onHistoryItemSelect(item) },
                                    onLongClick = {} // Handled via keyboard tap or click
                                )
                            }
                        }
                    }
                }

                // Vertical Divider between panels
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                )

                // Calculator Panel (Right side)
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Display(
                        expression = expression,
                        displayValue = displayValue,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.widthIn(max = 440.dp)
                    ) {
                        Keypad(
                            onDigitClick = { viewModel.onDigitClick(it) },
                            onOperatorClick = { viewModel.onOperatorClick(it) },
                            onDecimalClick = { viewModel.onDecimalClick() },
                            onPercentageClick = { viewModel.onPercentageClick() },
                            onToggleSignClick = { viewModel.onToggleSignClick() },
                            onClearClick = { viewModel.onClearClick() },
                            onEqualClick = { viewModel.onEqualClick() },
                            onDeleteClick = { viewModel.onDeleteClick() },
                            hapticEnabled = hapticEnabled,
                            isDark = isDark
                        )
                    }
                }
            }
        } else {
            // Mobile Layout: Traditional vertical structure
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Large display showing expression & numbers
                Display(
                    expression = expression,
                    displayValue = displayValue,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Standard Keypad grid
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Keypad(
                        onDigitClick = { viewModel.onDigitClick(it) },
                        onOperatorClick = { viewModel.onOperatorClick(it) },
                        onDecimalClick = { viewModel.onDecimalClick() },
                        onPercentageClick = { viewModel.onPercentageClick() },
                        onToggleSignClick = { viewModel.onToggleSignClick() },
                        onClearClick = { viewModel.onClearClick() },
                        onEqualClick = { viewModel.onEqualClick() },
                        onDeleteClick = { viewModel.onDeleteClick() },
                        hapticEnabled = hapticEnabled,
                        isDark = isDark
                    )
                }
            }
        }
    }

    // Modal Bottom Sheet displaying history on mobile devices
    if (showHistorySheet && !isTablet) {
        HistoryBottomSheet(
            historyList = historyList,
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    showHistorySheet = false
                }
            },
            onHistoryItemClick = { viewModel.onHistoryItemSelect(it) },
            onClearHistoryClick = { viewModel.clearAllHistory() }
        )
    }
}
