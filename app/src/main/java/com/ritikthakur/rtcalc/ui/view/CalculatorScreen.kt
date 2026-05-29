package com.ritikthakur.rtcalc.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    
    // UI state flows from ViewModel
    val expression by viewModel.expression.collectAsState()
    val displayValue by viewModel.displayValue.collectAsState()
    val historyList by viewModel.historyList.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val hapticEnabled by viewModel.hapticEnabled.collectAsState()
    val angleMode by viewModel.angleMode.collectAsState()
    val decimalPrecision by viewModel.decimalPrecision.collectAsState()
    val scientificNotation by viewModel.scientificNotation.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // Sheet and settings screen display states
    var showHistorySheet by remember { mutableStateOf(false) }
    var showSettingsScreen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Responsive setup: landscape or tablet triggers scientific modes
    val isTablet = configuration.screenWidthDp >= 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isSciMode = isTablet || isLandscape

    val systemDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val backgroundColor = if (isDark) DarkBackground else LightBackground

    // Show full screen settings overlay dialog
    if (showSettingsScreen) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { showSettingsScreen = false },
            properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
        ) {
            SettingsScreen(
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
                onDismiss = { showSettingsScreen = false }
            )
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "RTCalc",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        // Display active Angle mode indicator (RAD/DEG/GRAD)
                        Badge(
                            containerColor = Orange,
                            contentColor = Color.White,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = angleMode.name,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
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

                    // Settings gear icon
                    IconButton(onClick = { showSettingsScreen = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
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
                // Persistent History Panel with Search Bar (Left side)
                Column(
                    modifier = Modifier
                        .weight(1.2f)
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
                        if (historyList.isNotEmpty() || searchQuery.isNotEmpty()) {
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
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // History Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = { Text("Search history...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Orange,
                            cursorColor = Orange
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

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
                                    onLongClick = {},
                                    onDeleteClick = { viewModel.deleteHistoryItem(item) }
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

                // Scientific Calculator Panel (Right side)
                Column(
                    modifier = Modifier
                        .weight(2.2f)
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
                        modifier = Modifier.fillMaxWidth()
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
                            onFunctionClick = { viewModel.onFunctionClick(it) },
                            onMemoryClear = { viewModel.onMemoryClear() },
                            onMemoryRecall = { viewModel.onMemoryRecall() },
                            onMemoryStore = { viewModel.onMemoryStore() },
                            onMemoryAdd = { viewModel.onMemoryAdd() },
                            onMemorySubtract = { viewModel.onMemorySubtract() },
                            hapticEnabled = hapticEnabled,
                            isDark = isDark,
                            isScientific = true
                        )
                    }
                }
            }
        } else {
            // Mobile Layout (Adaptive: Portrait Standard vs Landscape Scientific)
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

                // Standard or Scientific Keypad grid based on Orientation
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
                        onFunctionClick = { viewModel.onFunctionClick(it) },
                        onMemoryClear = { viewModel.onMemoryClear() },
                        onMemoryRecall = { viewModel.onMemoryRecall() },
                        onMemoryStore = { viewModel.onMemoryStore() },
                        onMemoryAdd = { viewModel.onMemoryAdd() },
                        onMemorySubtract = { viewModel.onMemorySubtract() },
                        hapticEnabled = hapticEnabled,
                        isDark = isDark,
                        isScientific = isSciMode
                    )
                }
            }
        }
    }

    // Modal Bottom Sheet displaying history on mobile devices
    if (showHistorySheet && !isTablet) {
        HistoryBottomSheet(
            historyList = historyList,
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.setSearchQuery(it) },
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    showHistorySheet = false
                }
            },
            onHistoryItemClick = { viewModel.onHistoryItemSelect(it) },
            onDeleteItemClick = { viewModel.deleteHistoryItem(it) },
            onClearHistoryClick = { viewModel.clearAllHistory() }
        )
    }
}
