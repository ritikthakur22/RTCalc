package com.ritikthakur.rtcalc.ui.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.data.local.HistoryEntity
import com.ritikthakur.rtcalc.ui.theme.LightTextSecondary
import com.ritikthakur.rtcalc.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    historyList: List<HistoryEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onHistoryItemClick: (HistoryEntity) -> Unit,
    onDeleteItemClick: (HistoryEntity) -> Unit,
    onClearHistoryClick: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var showClearConfirm by remember { mutableStateOf(false) }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Clear History") },
            text = { Text("Are you sure you want to permanently clear all calculation logs?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearHistoryClick()
                        showClearConfirm = false
                    }
                ) {
                    Text("Clear All", color = Orange, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
        modifier = Modifier.fillMaxHeight(0.7f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(com.ritikthakur.rtcalc.R.string.title_history),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                if (historyList.isNotEmpty() || searchQuery.isNotEmpty()) {
                    Text(
                        text = context.getString(com.ritikthakur.rtcalc.R.string.clear_history),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Orange,
                        modifier = Modifier.clickable {
                            showClearConfirm = true
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // History Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
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
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
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
                            onClick = {
                                onHistoryItemClick(item)
                                onDismissRequest()
                            },
                            onLongClick = {
                                clipboardManager.setText(AnnotatedString(item.result))
                                Toast.makeText(context, context.getString(com.ritikthakur.rtcalc.R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
                            },
                            onDeleteClick = {
                                onDeleteItemClick(item)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BoxEmptyHistory(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = context.getString(com.ritikthakur.rtcalc.R.string.no_history),
            fontSize = 16.sp,
            color = LightTextSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryRowItem(
    item: HistoryEntity,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Calculation output text details
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.expression,
                fontSize = 14.sp,
                color = LightTextSecondary
            )
            Text(
                text = "= ${item.result}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Orange,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // Action Deletion Button
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}
