package com.ritikthakur.rtcalc.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.ritikthakur.rtcalc.data.local.HistoryDao
import com.ritikthakur.rtcalc.data.local.HistoryEntity
import com.ritikthakur.rtcalc.data.repository.AngleMode
import com.ritikthakur.rtcalc.data.repository.HistoryRepository
import com.ritikthakur.rtcalc.data.repository.SettingsRepository
import com.ritikthakur.rtcalc.data.repository.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var historyRepository: HistoryRepository
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Mock HistoryDao for HistoryRepository
        val mockDao = Mockito.mock(HistoryDao::class.java)
        // Emit dummy history items for search tests
        val dummyList = listOf(
            HistoryEntity(id = 1, expression = "10+20", result = "30"),
            HistoryEntity(id = 2, expression = "sin(90)", result = "1")
        )
        Mockito.`when`(mockDao.getAllHistory()).thenReturn(flow { emit(dummyList) })
        historyRepository = HistoryRepository(mockDao)

        // Mock SettingsRepository
        settingsRepository = Mockito.mock(SettingsRepository::class.java)
        Mockito.`when`(settingsRepository.themeModeFlow).thenReturn(MutableStateFlow(ThemeMode.SYSTEM))
        Mockito.`when`(settingsRepository.hapticEnabledFlow).thenReturn(MutableStateFlow(true))
        Mockito.`when`(settingsRepository.angleModeFlow).thenReturn(MutableStateFlow(AngleMode.DEGREE))
        Mockito.`when`(settingsRepository.decimalPrecisionFlow).thenReturn(MutableStateFlow(10))
        Mockito.`when`(settingsRepository.scientificNotationFlow).thenReturn(MutableStateFlow(false))

        viewModel = CalculatorViewModel(
            historyRepository = historyRepository,
            settingsRepository = settingsRepository,
            savedStateHandle = SavedStateHandle()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFirstOperandMultiDigitEntry() = runTest {
        viewModel.onDigitClick("7")
        viewModel.onDigitClick("8")
        assertEquals("78", viewModel.expression.value)
        assertEquals("78", viewModel.displayValue.value)
    }

    @Test
    fun testSecondOperandMultiDigitEntry() = runTest {
        viewModel.onDigitClick("1")
        viewModel.onDigitClick("2")
        viewModel.onOperatorClick("+")
        viewModel.onDigitClick("3")
        viewModel.onDigitClick("4")
        
        assertEquals("12+34", viewModel.expression.value)
        assertEquals("34", viewModel.displayValue.value)
    }

    @Test
    fun testDecimalClickAppendsDot() = runTest {
        viewModel.onDigitClick("1")
        viewModel.onDecimalClick()
        viewModel.onDigitClick("2")
        viewModel.onDigitClick("3")
        assertEquals("1.23", viewModel.expression.value)
        assertEquals("1.23", viewModel.displayValue.value)
    }

    @Test
    fun testDeleteBehavior() = runTest {
        viewModel.onDigitClick("8")
        viewModel.onDigitClick("7")
        viewModel.onDigitClick("3")
        
        viewModel.onDeleteClick()
        assertEquals("87", viewModel.expression.value)
        assertEquals("87", viewModel.displayValue.value)
    }

    @Test
    fun testStateRestoration() = runTest {
        val restoredHandle = SavedStateHandle(
            mapOf(
                "expression" to "123+45",
                "display_value" to "45"
            )
        )
        val restoredViewModel = CalculatorViewModel(
            historyRepository = historyRepository,
            settingsRepository = settingsRepository,
            savedStateHandle = restoredHandle
        )
        
        assertEquals("123+45", restoredViewModel.expression.value)
        assertEquals("45", restoredViewModel.displayValue.value)
    }

    @Test
    fun testScientificFunctionsAppendsCorrectly() = runTest {
        viewModel.onFunctionClick("sin")
        assertEquals("sin(", viewModel.expression.value)

        viewModel.onClearClick()
        viewModel.onFunctionClick("pi")
        assertEquals("pi", viewModel.expression.value)
    }

    @Test
    fun testMemoryOperations() = runTest {
        viewModel.onDigitClick("5")
        viewModel.onMemoryStore() // stores 5 in memory
        
        assertEquals("5", viewModel.memoryValue.value)
        
        viewModel.onClearClick()
        viewModel.onMemoryRecall() // recalls 5 to active entry
        assertEquals("5", viewModel.expression.value)
        
        viewModel.onMemoryClear() // clears memory
        assertEquals("0", viewModel.memoryValue.value)
    }

    @Test
    fun testHistorySearchQueryFiltering() = runTest {
        // Initially should show all dummy items
        assertEquals(2, viewModel.historyList.value.size)

        // Filter by "sin"
        viewModel.setSearchQuery("sin")
        assertEquals(1, viewModel.historyList.value.size)
        assertEquals("sin(90)", viewModel.historyList.value.first().expression)

        // Filter by invalid text
        viewModel.setSearchQuery("invalid")
        assertTrue(viewModel.historyList.value.isEmpty())
    }

    @Test
    fun testCursorInsertion() = runTest {
        viewModel.onDigitClick("1")
        viewModel.onDigitClick("2")
        viewModel.onDigitClick("3")
        viewModel.onOperatorClick("+")
        viewModel.onDigitClick("4")
        viewModel.onDigitClick("5")
        viewModel.onDigitClick("6") // "123+456"
        
        // Tap between "123" and "+456" (index 3)
        viewModel.updateExpressionValue(androidx.compose.ui.text.input.TextFieldValue("123+456", androidx.compose.ui.text.TextRange(3)))
        viewModel.onDigitClick("7") // "1237+456"
        
        assertEquals("1237+456", viewModel.expressionVal.value.text)
        assertEquals(4, viewModel.expressionVal.value.selection.start) // Cursor moved past '7'
    }

    @Test
    fun testSelectionDeletion() = runTest {
        viewModel.onDigitClick("1")
        viewModel.onDigitClick("2")
        viewModel.onDigitClick("3")
        viewModel.onDigitClick("4") // "1234"
        
        // Select "23" (index 1 to 3)
        viewModel.updateExpressionValue(androidx.compose.ui.text.input.TextFieldValue("1234", androidx.compose.ui.text.TextRange(1, 3)))
        viewModel.onDeleteClick() // deletes "23", expression becomes "14"
        
        assertEquals("14", viewModel.expressionVal.value.text)
        assertEquals(1, viewModel.expressionVal.value.selection.start)
    }

    @Test
    fun testUndoRedoFlow() = runTest {
        viewModel.onDigitClick("1")
        viewModel.onDigitClick("2") // "12"
        
        viewModel.undo() // reverts to "1"
        assertEquals("1", viewModel.expressionVal.value.text)
        
        viewModel.redo() // restores to "12"
        assertEquals("12", viewModel.expressionVal.value.text)
    }
}
