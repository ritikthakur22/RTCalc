package com.ritikthakur.rtcalc.ui.viewmodel

import com.ritikthakur.rtcalc.data.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    @Mock
    private lateinit var mockRepository: CurrencyRepository

    private lateinit var viewModel: CurrencyViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialization fetches rates`() = runTest {
        `when`(mockRepository.getRates("USD")).thenReturn(mapOf("EUR" to 0.85))
        
        viewModel = CurrencyViewModel(mockRepository)
        advanceUntilIdle()

        assertEquals(0.85, viewModel.rates.value["EUR"]!!, 0.001)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `swapCurrencies updates correctly`() = runTest {
        `when`(mockRepository.getRates("USD")).thenReturn(mapOf("EUR" to 0.85))
        `when`(mockRepository.getRates("EUR")).thenReturn(mapOf("USD" to 1.18))
        
        viewModel = CurrencyViewModel(mockRepository)
        advanceUntilIdle()

        assertEquals("USD", viewModel.sourceCurrency.value)
        assertEquals("EUR", viewModel.targetCurrency.value)

        viewModel.swapCurrencies()
        advanceUntilIdle()

        assertEquals("EUR", viewModel.sourceCurrency.value)
        assertEquals("USD", viewModel.targetCurrency.value)
        assertEquals(1.18, viewModel.rates.value["USD"]!!, 0.001)
    }

    @Test
    fun `amount updates correctly with validation`() = runTest {
        `when`(mockRepository.getRates("USD")).thenReturn(mapOf("EUR" to 0.85))
        viewModel = CurrencyViewModel(mockRepository)
        
        viewModel.setAmount("12.5")
        assertEquals("12.5", viewModel.amount.value)

        viewModel.setAmount("12.5.5") // invalid
        assertEquals("12.5", viewModel.amount.value) // remains old
    }
}
