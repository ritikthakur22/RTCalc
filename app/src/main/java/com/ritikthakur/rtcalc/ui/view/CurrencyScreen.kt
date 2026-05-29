package com.ritikthakur.rtcalc.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.DarkBackground
import com.ritikthakur.rtcalc.ui.theme.LightBackground
import com.ritikthakur.rtcalc.ui.theme.Orange
import com.ritikthakur.rtcalc.ui.viewmodel.CurrencyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    viewModel: CurrencyViewModel,
    isDark: Boolean,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rates by viewModel.rates.collectAsState()
    val sourceCurrency by viewModel.sourceCurrency.collectAsState()
    val targetCurrency by viewModel.targetCurrency.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val cardColor = if (isDark) Color(0xFF1C1C1E) else Color(0xFFFFFFFF)
    val textColor = MaterialTheme.colorScheme.onBackground

    var showSourcePicker by remember { mutableStateOf(false) }
    var showTargetPicker by remember { mutableStateOf(false) }

    val popularCurrencies = listOf("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR", "NPR")
    val availableCurrencies = if (rates.isNotEmpty()) rates.keys.toList().sorted() else popularCurrencies.sorted()

    val amountDouble = amount.toDoubleOrNull() ?: 0.0
    val targetRate = rates[targetCurrency] ?: 0.0
    val convertedAmount = amountDouble * targetRate

    Scaffold(
        modifier = modifier.fillMaxSize().background(backgroundColor),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(imageVector = androidx.compose.material.icons.Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                title = { Text("Currency Converter", fontWeight = FontWeight.Bold, color = textColor) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = { viewModel.fetchRates() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Source Currency Card
            CurrencyCard(
                label = "You send",
                currency = sourceCurrency,
                amount = amount,
                isReadOnly = false,
                onAmountChange = { viewModel.setAmount(it) },
                onCurrencyClick = { showSourcePicker = true },
                cardColor = cardColor,
                textColor = textColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Swap Button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Orange)
                    .clickable { viewModel.swapCurrencies() },
                contentAlignment = Alignment.Center
            ) {
                Text("⇅", color = Color.White, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Target Currency Card
            CurrencyCard(
                label = "They receive",
                currency = targetCurrency,
                amount = if (rates.isEmpty()) "Loading..." else String.format("%.2f", convertedAmount),
                isReadOnly = true,
                onAmountChange = {},
                onCurrencyClick = { showTargetPicker = true },
                cardColor = cardColor,
                textColor = textColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Exchange Rate Info
            if (rates.isNotEmpty()) {
                Text(
                    text = "1 $sourceCurrency = ${String.format("%.4f", targetRate)} $targetCurrency",
                    color = textColor.copy(alpha = 0.6f),
                    fontSize = 14.sp
                )
            }

            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(color = Orange)
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage!!, color = Color.Red)
            }
        }
    }

    if (showSourcePicker) {
        CurrencyPicker(
            currencies = availableCurrencies,
            onDismiss = { showSourcePicker = false },
            onSelect = { 
                viewModel.setSourceCurrency(it)
                showSourcePicker = false 
            }
        )
    }

    if (showTargetPicker) {
        CurrencyPicker(
            currencies = availableCurrencies,
            onDismiss = { showTargetPicker = false },
            onSelect = { 
                viewModel.setTargetCurrency(it)
                showTargetPicker = false 
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyCard(
    label: String,
    currency: String,
    amount: String,
    isReadOnly: Boolean,
    onAmountChange: (String) -> Unit,
    onCurrencyClick: () -> Unit,
    cardColor: Color,
    textColor: Color
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = label, color = textColor.copy(alpha = 0.5f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onCurrencyClick() }
                ) {
                    Text(text = currency, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Currency", tint = textColor)
                }
                
                TextField(
                    value = amount,
                    onValueChange = onAmountChange,
                    readOnly = isReadOnly,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.End,
                        color = textColor
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPicker(
    currencies: List<String>,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(currencies) { currency ->
                Text(
                    text = currency,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(currency) }
                        .padding(16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
