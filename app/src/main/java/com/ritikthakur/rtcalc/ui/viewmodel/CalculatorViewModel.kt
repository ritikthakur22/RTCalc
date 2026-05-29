package com.ritikthakur.rtcalc.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritikthakur.rtcalc.data.local.HistoryEntity
import com.ritikthakur.rtcalc.data.repository.HistoryRepository
import com.ritikthakur.rtcalc.data.repository.SettingsRepository
import com.ritikthakur.rtcalc.data.repository.ThemeMode
import com.ritikthakur.rtcalc.domain.ExpressionEvaluator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val settingsRepository: SettingsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Expose flows to the UI for reactive updates
    val expression: StateFlow<String> = savedStateHandle.getStateFlow("expression", "")
    val displayValue: StateFlow<String> = savedStateHandle.getStateFlow("display_value", "0")

    // Database History Flow
    val historyList: StateFlow<List<HistoryEntity>> = historyRepository.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Theme Mode Flow
    val themeMode: StateFlow<ThemeMode> = settingsRepository.themeModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    // Haptic Feedback Flow
    val hapticEnabled: StateFlow<Boolean> = settingsRepository.hapticEnabledFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    // Synchronous helper getters to read directly from SavedStateHandle map
    private fun getExpression(): String = savedStateHandle.get<String>("expression") ?: ""
    private fun getDisplayValue(): String = savedStateHandle.get<String>("display_value") ?: "0"
    private fun isResultState(): Boolean = savedStateHandle.get<Boolean>("is_result") ?: false

    fun onDigitClick(digit: String) {
        val isResult = isResultState()
        if (isResult) {
            updateExpression(digit)
            updateDisplayValue(digit)
            savedStateHandle["is_result"] = false
        } else {
            val currentExpr = getExpression()
            val newExpr = if (currentExpr == "0" || currentExpr.isEmpty()) digit else currentExpr + digit
            updateExpression(newExpr)
            updateDisplayValue(extractActiveNumber(newExpr))
        }
    }

    fun onDecimalClick() {
        val isResult = isResultState()
        val currentExpr = if (isResult) {
            savedStateHandle["is_result"] = false
            "0"
        } else {
            getExpression()
        }
        val activeNum = extractActiveNumber(currentExpr)
        
        if (!activeNum.contains(".")) {
            val newExpr = if (currentExpr.isEmpty() || isOperator(currentExpr.last().toString())) {
                currentExpr + "0."
            } else {
                currentExpr + "."
            }
            updateExpression(newExpr)
            updateDisplayValue(extractActiveNumber(newExpr))
        }
    }

    fun onOperatorClick(operator: String) {
        savedStateHandle["is_result"] = false
        val currentExpr = getExpression()
        if (currentExpr.isEmpty()) {
            if (operator == "-") {
                updateExpression("-")
                updateDisplayValue("-")
            }
            return
        }

        val lastChar = currentExpr.last().toString()
        if (isOperator(lastChar)) {
            val newExpr = currentExpr.dropLast(1) + operator
            updateExpression(newExpr)
        } else {
            updateExpression(currentExpr + operator)
        }
    }

    fun onPercentageClick() {
        savedStateHandle["is_result"] = false
        val currentExpr = getExpression()
        if (currentExpr.isNotEmpty() && !isOperator(currentExpr.last().toString())) {
            val newExpr = currentExpr + "%"
            updateExpression(newExpr)
            updateDisplayValue(extractActiveNumber(newExpr))
        }
    }

    fun onToggleSignClick() {
        savedStateHandle["is_result"] = false
        val currentExpr = getExpression()
        val newExpr = toggleLastNumberSign(currentExpr)
        updateExpression(newExpr)
        updateDisplayValue(extractActiveNumber(newExpr))
    }

    fun onDeleteClick() {
        savedStateHandle["is_result"] = false
        val currentExpr = getExpression()
        if (currentExpr.isNotEmpty()) {
            val newExpr = currentExpr.dropLast(1)
            updateExpression(newExpr)
            val active = extractActiveNumber(newExpr)
            updateDisplayValue(if (active.isEmpty()) "0" else active)
        }
    }

    fun onClearClick() {
        savedStateHandle["is_result"] = false
        updateExpression("")
        updateDisplayValue("0")
    }

    fun onEqualClick() {
        val currentExpr = getExpression()
        if (currentExpr.isBlank()) return

        val evalResult = ExpressionEvaluator.evaluate(currentExpr)
        if (!evalResult.startsWith("Error")) {
            viewModelScope.launch {
                historyRepository.insertHistory(currentExpr, evalResult)
            }
            updateDisplayValue(evalResult)
            updateExpression(evalResult)
            savedStateHandle["is_result"] = true
        } else {
            updateDisplayValue(evalResult)
        }
    }

    fun onHistoryItemSelect(history: HistoryEntity) {
        savedStateHandle["is_result"] = false
        updateExpression(history.expression)
        updateDisplayValue(history.result)
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            historyRepository.clearHistory()
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
        }
    }

    fun setHapticEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setHapticEnabled(enabled)
        }
    }

    private fun updateExpression(value: String) {
        savedStateHandle["expression"] = value
    }

    private fun updateDisplayValue(value: String) {
        savedStateHandle["display_value"] = value
    }

    private fun isOperator(token: String): Boolean {
        return token == "+" || token == "-" || token == "×" || token == "÷"
    }

    private fun extractActiveNumber(expr: String): String {
        if (expr.isEmpty()) return "0"
        var i = expr.length - 1
        while (i >= 0 && (expr[i].isDigit() || expr[i] == '.' || expr[i] == '%')) {
            i--
        }
        val start = i + 1
        if (start > 0 && expr[start - 1] == '-') {
            val idxBeforeMinus = start - 2
            val isUnary = idxBeforeMinus < 0 || expr[idxBeforeMinus] in listOf('+', '-', '×', '÷')
            if (isUnary) {
                return expr.substring(start - 1)
            }
        }
        return expr.substring(start)
    }

    private fun toggleLastNumberSign(expr: String): String {
        if (expr.isEmpty()) return "-"
        var i = expr.length - 1
        while (i >= 0 && (expr[i].isDigit() || expr[i] == '.' || expr[i] == '%')) {
            i--
        }
        val numStart = i + 1
        if (numStart > 0 && expr[numStart - 1] == '-') {
            val idxBeforeMinus = numStart - 2
            val isUnary = idxBeforeMinus < 0 || expr[idxBeforeMinus] in listOf('+', '-', '×', '÷')
            if (isUnary) {
                return expr.substring(0, numStart - 1) + expr.substring(numStart)
            }
        }
        return expr.substring(0, numStart) + "-" + expr.substring(numStart)
    }
}
