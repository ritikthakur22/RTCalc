package com.ritikthakur.rtcalc.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.ritikthakur.rtcalc.data.local.HistoryDao
import com.ritikthakur.rtcalc.data.local.HistoryEntity
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
        Mockito.`when`(mockDao.getAllHistory()).thenReturn(flow { emit(emptyList<HistoryEntity>()) })
        historyRepository = HistoryRepository(mockDao)

        // Mock SettingsRepository
        settingsRepository = Mockito.mock(SettingsRepository::class.java)
        Mockito.`when`(settingsRepository.themeModeFlow).thenReturn(MutableStateFlow(ThemeMode.SYSTEM))
        Mockito.`when`(settingsRepository.hapticEnabledFlow).thenReturn(MutableStateFlow(true))

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
    fun testClearResetsValues() = runTest {
        viewModel.onDigitClick("8")
        viewModel.onClearClick()
        assertEquals("", viewModel.expression.value)
        assertEquals("0", viewModel.displayValue.value)
    }
}
