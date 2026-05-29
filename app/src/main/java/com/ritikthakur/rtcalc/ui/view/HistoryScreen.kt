package com.ritikthakur.rtcalc.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.DarkBackground
import com.ritikthakur.rtcalc.ui.theme.LightBackground
import com.ritikthakur.rtcalc.ui.theme.Orange
import com.ritikthakur.rtcalc.ui.viewmodel.CalculatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: CalculatorViewModel,
    isDark: Boolean,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val historyList by viewModel.historyList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val textColor = MaterialTheme.colorScheme.onBackground
    val context = LocalContext.current

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
                title = { Text("History", fontWeight = FontWeight.Bold, color = textColor) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    if (historyList.isNotEmpty() || searchQuery.isNotEmpty()) {
                        Text(
                            text = "Clear",
                            color = Orange,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable { viewModel.clearAllHistory() }
                                .padding(16.dp)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
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
                            onClick = { 
                                viewModel.onHistoryItemSelect(item)
                                onBackClick() // Go back to calculator
                            },
                            onLongClick = {},
                            onDeleteClick = { viewModel.deleteHistoryItem(item) }
                        )
                    }
                }
            }
        }
    }
}
