package com.ritikthakur.rtcalc.ui.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
import kotlinx.coroutines.flow.asStateFlow
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

    private val _expressionVal = MutableStateFlow(
        TextFieldValue(
            text = savedStateHandle.get<String>("expression") ?: "",
            selection = TextRange(
                savedStateHandle.get<Int>("selection_start") ?: (savedStateHandle.get<String>("expression")?.length ?: 0),
                savedStateHandle.get<Int>("selection_end") ?: (savedStateHandle.get<String>("expression")?.length ?: 0)
            )
        )
    )
    val expressionVal: StateFlow<TextFieldValue> = _expressionVal.asStateFlow()

    // Undo/Redo history stacks
    private val undoStack = java.util.Stack<TextFieldValue>()
    private val redoStack = java.util.Stack<TextFieldValue>()

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

    private fun getExpression(): String = savedStateHandle.get<String>("expression") ?: ""
    private fun getDisplayValue(): String = savedStateHandle.get<String>("display_value") ?: "0"
    private fun isResultState(): Boolean = savedStateHandle.get<Boolean>("is_result") ?: false

    // Update active expression and sync to SavedStateHandle and flows
    private fun updateExpressionVal(newValue: TextFieldValue, isUndoRedoAction: Boolean = false) {
        if (!isUndoRedoAction) {
            val current = _expressionVal.value
            if (current.text != newValue.text) {
                undoStack.push(current)
                redoStack.clear()
            }
        }
        _expressionVal.value = newValue
        savedStateHandle["expression"] = newValue.text
        savedStateHandle["selection_start"] = newValue.selection.start
        savedStateHandle["selection_end"] = newValue.selection.end
    }

    fun updateExpressionValue(newValue: TextFieldValue) {
        // Keeps user cursor navigation & text selection edits updated
        updateExpressionVal(newValue)
    }

    private fun insertTextAtCursor(insertedText: String) {
        val current = _expressionVal.value
        val text = current.text
        val selection = current.selection
        val start = selection.start
        val end = selection.end
        
        val newText = text.substring(0, start) + insertedText + text.substring(end)
        val newSelection = TextRange(start + insertedText.length)
        
        updateExpressionVal(TextFieldValue(newText, newSelection))
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val current = _expressionVal.value
            redoStack.push(current)
            val previous = undoStack.pop()
            updateExpressionVal(previous, isUndoRedoAction = true)
            updateDisplayValue(extractActiveNumberNearCursor(previous.text, previous.selection.start))
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val current = _expressionVal.value
            undoStack.push(current)
            val next = redoStack.pop()
            updateExpressionVal(next, isUndoRedoAction = true)
            updateDisplayValue(extractActiveNumberNearCursor(next.text, next.selection.start))
        }
    }

    fun onDigitClick(digit: String) {
        val isResult = isResultState()
        if (isResult) {
            updateExpressionVal(TextFieldValue(digit, TextRange(digit.length)))
            updateDisplayValue(digit)
            savedStateHandle["is_result"] = false
        } else {
            insertTextAtCursor(digit)
            updateDisplayValue(extractActiveNumberNearCursor(_expressionVal.value.text, _expressionVal.value.selection.start))
        }
    }

    fun onDecimalClick() {
        val isResult = isResultState()
        if (isResult) {
            savedStateHandle["is_result"] = false
            updateExpressionVal(TextFieldValue("0.", TextRange(2)))
            updateDisplayValue("0.")
            return
        }
        val current = _expressionVal.value
        val text = current.text
        val selection = current.selection
        val cursor = selection.start
        
        val activeNum = extractActiveNumberNearCursor(text, cursor)
        if (!activeNum.contains(".")) {
            val insert = if (cursor == 0 || text[cursor - 1] in listOf('+', '-', '×', '÷', '^', ',', '(', ' ')) "0." else "."
            insertTextAtCursor(insert)
            updateDisplayValue(extractActiveNumberNearCursor(_expressionVal.value.text, _expressionVal.value.selection.start))
        }
    }

    fun onOperatorClick(operator: String) {
        savedStateHandle["is_result"] = false
        val current = _expressionVal.value
        val text = current.text
        val selection = current.selection
        val cursor = selection.start

        if (cursor == 0) {
            if (operator == "-") {
                insertTextAtCursor("-")
                updateDisplayValue("-")
            }
            return
        }

        val lastChar = text[cursor - 1].toString()
        if (isOperator(lastChar)) {
            val newText = text.substring(0, cursor - 1) + operator + text.substring(cursor)
            updateExpressionVal(TextFieldValue(newText, TextRange(cursor)))
        } else {
            insertTextAtCursor(operator)
        }
    }

    fun onPercentageClick() {
        savedStateHandle["is_result"] = false
        val current = _expressionVal.value
        val text = current.text
        val selection = current.selection
        val cursor = selection.start
        
        if (cursor > 0 && !isOperator(text[cursor - 1].toString())) {
            insertTextAtCursor("%")
            updateDisplayValue(extractActiveNumberNearCursor(_expressionVal.value.text, _expressionVal.value.selection.start))
        }
    }

    fun onToggleSignClick() {
        savedStateHandle["is_result"] = false
        val current = _expressionVal.value
        val text = current.text
        val selection = current.selection
        val cursor = selection.start
        
        var i = cursor - 1
        while (i >= 0 && (text[i].isDigit() || text[i] == '.' || text[i] == '%' || text[i] == '(' || text[i] == ')' || text[i].isLetter() || text[i] == ',')) {
            i--
        }
        val numStart = i + 1
        
        val newText: String
        val newCursor: Int
        if (numStart > 0 && text[numStart - 1] == '-') {
            val idxBeforeMinus = numStart - 2
            val isUnary = idxBeforeMinus < 0 || text[idxBeforeMinus] in listOf('+', '-', '×', '÷', '^', ',')
            if (isUnary) {
                newText = text.substring(0, numStart - 1) + text.substring(numStart)
                newCursor = cursor - 1
            } else {
                newText = text.substring(0, numStart) + "-" + text.substring(numStart)
                newCursor = cursor + 1
            }
        } else {
            newText = text.substring(0, numStart) + "-" + text.substring(numStart)
            newCursor = cursor + 1
        }
        updateExpressionVal(TextFieldValue(newText, TextRange(newCursor)))
    }

    fun onDeleteClick() {
        savedStateHandle["is_result"] = false
        val current = _expressionVal.value
        val text = current.text
        val selection = current.selection
        val start = selection.start
        val end = selection.end

        if (start != end) {
            val newText = text.substring(0, start) + text.substring(end)
            updateExpressionVal(TextFieldValue(newText, TextRange(start)))
            val active = extractActiveNumberNearCursor(newText, start)
            updateDisplayValue(if (active.isEmpty()) "0" else active)
        } else if (start > 0) {
            val newText = text.substring(0, start - 1) + text.substring(start)
            updateExpressionVal(TextFieldValue(newText, TextRange(start - 1)))
            val active = extractActiveNumberNearCursor(newText, start - 1)
            updateDisplayValue(if (active.isEmpty()) "0" else active)
        }
    }

    fun onClearClick() {
        savedStateHandle["is_result"] = false
        updateExpressionVal(TextFieldValue("", TextRange.Zero))
        updateDisplayValue("0")
    }

    fun onEqualClick() {
        val current = _expressionVal.value
        val currentExpr = current.text
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
            updateExpressionVal(TextFieldValue(evalResult, TextRange(evalResult.length)))
            savedStateHandle["is_result"] = true
        } else {
            updateDisplayValue(evalResult)
        }
    }

    fun onFunctionClick(functionName: String) {
        val isResult = isResultState()
        
        val suffix = when (functionName) {
            "sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh",
            "log", "ln", "log2", "sqrt", "abs", "floor", "ceil", "round", "gcd", "lcm" -> "$functionName("
            "nPr" -> " P "
            "nCr" -> " C "
            "1/x" -> "1/("
            "mod" -> " mod "
            else -> functionName
        }

        if (isResult) {
            updateExpressionVal(TextFieldValue(suffix, TextRange(suffix.length)))
            updateDisplayValue(suffix)
            savedStateHandle["is_result"] = false
        } else {
            insertTextAtCursor(suffix)
            updateDisplayValue(extractActiveNumberNearCursor(_expressionVal.value.text, _expressionVal.value.selection.start))
        }
    }

    fun onMemoryClear() {
        savedStateHandle["memory_value"] = "0"
    }

    fun onMemoryRecall() {
        val mem = savedStateHandle.get<String>("memory_value") ?: "0"
        val isResult = isResultState()
        
        if (isResult) {
            updateExpressionVal(TextFieldValue(mem, TextRange(mem.length)))
            updateDisplayValue(mem)
            savedStateHandle["is_result"] = false
        } else {
            insertTextAtCursor(mem)
            updateDisplayValue(extractActiveNumberNearCursor(_expressionVal.value.text, _expressionVal.value.selection.start))
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

    fun onHistoryItemSelect(history: HistoryEntity) {
        savedStateHandle["is_result"] = false
        updateExpressionVal(TextFieldValue(history.expression, TextRange(history.expression.length)))
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

    private fun extractActiveNumberNearCursor(text: String, cursor: Int): String {
        if (text.isEmpty() || cursor <= 0) return "0"
        var i = cursor - 1
        while (i >= 0 && (text[i].isDigit() || text[i] == '.' || text[i] == '%' || text[i] == '(' || text[i] == ')' || text[i].isLetter() || text[i] == ',')) {
            i--
        }
        val start = i + 1
        return text.substring(start, cursor)
    }
}
