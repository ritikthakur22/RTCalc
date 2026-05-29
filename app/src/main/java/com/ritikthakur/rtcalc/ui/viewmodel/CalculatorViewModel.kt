package com.ritikthakur.rtcalc.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritikthakur.rtcalc.data.local.HistoryEntity
import com.ritikthakur.rtcalc.data.repository.AngleMode
import com.ritikthakur.rtcalc.data.repository.HistoryRepository
import com.ritikthakur.rtcalc.data.repository.SettingsRepository
import com.ritikthakur.rtcalc.data.repository.ThemeMode
import com.ritikthakur.rtcalc.domain.ExpressionEvaluator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
    val memoryValue: StateFlow<String> = savedStateHandle.getStateFlow("memory_value", "0")

    // History Search Query
    val searchQuery = MutableStateFlow("")

    // Database History Flow combined with Search Query
    val historyList: StateFlow<List<HistoryEntity>> = historyRepository.getAllHistory()
        .combine(searchQuery) { list, query ->
            if (query.isBlank()) {
                list
            } else {
                list.filter {
                    it.expression.contains(query, ignoreCase = true) ||
                    it.result.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Theme Mode Flow
    val themeMode: StateFlow<ThemeMode> = settingsRepository.themeModeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeMode.SYSTEM)

    // Haptic Feedback Flow
    val hapticEnabled: StateFlow<Boolean> = settingsRepository.hapticEnabledFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    // Angle Mode Flow
    val angleMode: StateFlow<AngleMode> = settingsRepository.angleModeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, AngleMode.DEGREE)

    // Decimal Precision Flow
    val decimalPrecision: StateFlow<Int> = settingsRepository.decimalPrecisionFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, 10)

    // Scientific Notation Flow
    val scientificNotation: StateFlow<Boolean> = settingsRepository.scientificNotationFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

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

        val evalResult = ExpressionEvaluator.evaluate(
            expression = currentExpr,
            angleMode = angleMode.value,
            precision = decimalPrecision.value,
            useScientific = scientificNotation.value
        )
        
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

    // Scientific specific controls
    fun onFunctionClick(functionName: String) {
        val isResult = isResultState()
        val currentExpr = getExpression()
        
        // Exponent handles itself, comma for multiple parameters (e.g. nCr(5,2))
        val suffix = when (functionName) {
            "sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh",
            "log", "ln", "log2", "sqrt", "abs", "floor", "ceil", "round", "nCr", "nPr", "gcd", "lcm" -> "$functionName("
            else -> functionName
        }

        if (isResult) {
            updateExpression(suffix)
            updateDisplayValue(suffix)
            savedStateHandle["is_result"] = false
        } else {
            val newExpr = if (currentExpr == "0" || currentExpr.isEmpty()) suffix else currentExpr + suffix
            updateExpression(newExpr)
            updateDisplayValue(extractActiveNumber(newExpr))
        }
    }

    // Memory operations
    fun onMemoryClear() {
        savedStateHandle["memory_value"] = "0"
    }

    fun onMemoryRecall() {
        val mem = savedStateHandle.get<String>("memory_value") ?: "0"
        val isResult = isResultState()
        val currentExpr = getExpression()
        
        if (isResult) {
            updateExpression(mem)
            updateDisplayValue(mem)
            savedStateHandle["is_result"] = false
        } else {
            val newExpr = if (currentExpr == "0" || currentExpr.isEmpty()) mem else currentExpr + mem
            updateExpression(newExpr)
            updateDisplayValue(extractActiveNumber(newExpr))
        }
    }

    fun onMemoryStore() {
        val disp = getDisplayValue()
        if (disp.isNotEmpty() && !disp.startsWith("Error")) {
            savedStateHandle["memory_value"] = disp
        }
    }

    fun onMemoryAdd() {
        val disp = getDisplayValue()
        if (disp.isNotEmpty() && !disp.startsWith("Error")) {
            val currentMem = (savedStateHandle.get<String>("memory_value") ?: "0").toDoubleOrNull() ?: 0.0
            val addedVal = disp.toDoubleOrNull() ?: 0.0
            val formatted = ExpressionEvaluator.evaluate(
                expression = "$currentMem + $addedVal",
                angleMode = angleMode.value,
                precision = decimalPrecision.value,
                useScientific = false
            )
            if (!formatted.startsWith("Error")) {
                savedStateHandle["memory_value"] = formatted
            }
        }
    }

    fun onMemorySubtract() {
        val disp = getDisplayValue()
        if (disp.isNotEmpty() && !disp.startsWith("Error")) {
            val currentMem = (savedStateHandle.get<String>("memory_value") ?: "0").toDoubleOrNull() ?: 0.0
            val subbedVal = disp.toDoubleOrNull() ?: 0.0
            val formatted = ExpressionEvaluator.evaluate(
                expression = "$currentMem - $subbedVal",
                angleMode = angleMode.value,
                precision = decimalPrecision.value,
                useScientific = false
            )
            if (!formatted.startsWith("Error")) {
                savedStateHandle["memory_value"] = formatted
            }
        }
    }

    // History and Search actions
    fun onHistoryItemSelect(history: HistoryEntity) {
        savedStateHandle["is_result"] = false
        updateExpression(history.expression)
        updateDisplayValue(history.result)
    }

    fun deleteHistoryItem(history: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.deleteHistory(history)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            historyRepository.clearHistory()
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    // Settings actions
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

    fun setAngleMode(mode: AngleMode) {
        viewModelScope.launch {
            settingsRepository.setAngleMode(mode)
        }
    }

    fun setDecimalPrecision(precision: Int) {
        viewModelScope.launch {
            settingsRepository.setDecimalPrecision(precision)
        }
    }

    fun setScientificNotation(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setScientificNotation(enabled)
        }
    }

    private fun updateExpression(value: String) {
        savedStateHandle["expression"] = value
    }

    private fun updateDisplayValue(value: String) {
        savedStateHandle["display_value"] = value
    }

    private fun isOperator(token: String): Boolean {
        return token == "+" || token == "-" || token == "×" || token == "÷" || token == "^" || token == ","
    }

    private fun extractActiveNumber(expr: String): String {
        if (expr.isEmpty()) return "0"
        var i = expr.length - 1
        // Scan backwards over characters belonging to the active number or parenthesis levels
        while (i >= 0 && (expr[i].isDigit() || expr[i] == '.' || expr[i] == '%' || expr[i] == '(' || expr[i] == ')' || expr[i].isLetter() || expr[i] == ',')) {
            i--
        }
        val start = i + 1
        if (start > 0 && expr[start - 1] == '-') {
            val idxBeforeMinus = start - 2
            val isUnary = idxBeforeMinus < 0 || expr[idxBeforeMinus] in listOf('+', '-', '×', '÷', '^', ',')
            if (isUnary) {
                return expr.substring(start - 1)
            }
        }
        val res = expr.substring(start)
        return if (res.isEmpty()) "0" else res
    }

    private fun toggleLastNumberSign(expr: String): String {
        if (expr.isEmpty()) return "-"
        var i = expr.length - 1
        while (i >= 0 && (expr[i].isDigit() || expr[i] == '.' || expr[i] == '%' || expr[i] == '(' || expr[i] == ')' || expr[i].isLetter() || expr[i] == ',')) {
            i--
        }
        val numStart = i + 1
        if (numStart > 0 && expr[numStart - 1] == '-') {
            val idxBeforeMinus = numStart - 2
            val isUnary = idxBeforeMinus < 0 || expr[idxBeforeMinus] in listOf('+', '-', '×', '÷', '^', ',')
            if (isUnary) {
                return expr.substring(0, numStart - 1) + expr.substring(numStart)
            }
        }
        return expr.substring(0, numStart) + "-" + expr.substring(numStart)
    }
}
