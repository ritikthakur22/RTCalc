package com.ritikthakur.rtcalc.data.repository

import com.ritikthakur.rtcalc.data.local.CurrencyDao
import com.ritikthakur.rtcalc.data.local.CurrencyRateEntity
import com.ritikthakur.rtcalc.data.remote.CurrencyApi
import com.ritikthakur.rtcalc.data.remote.CurrencyResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CurrencyRepositoryTest {

    @Mock
    private lateinit var mockApi: CurrencyApi

    @Mock
    private lateinit var mockDao: CurrencyDao

    private lateinit var repository: CurrencyRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = CurrencyRepository(mockApi, mockDao)
    }

    @Test
    fun `getRates returns cached rates if valid`() = runTest {
        val json = """{"USD":1.0,"EUR":0.85}"""
        val entity = CurrencyRateEntity("USD", json, System.currentTimeMillis())
        `when`(mockDao.getRatesForBase("USD")).thenReturn(entity)

        val result = repository.getRates("USD")
        assertEquals(0.85, result["EUR"]!!, 0.001)
        verifyNoInteractions(mockApi)
    }

    @Test
    fun `getRates fetches from API if cache expired or missing`() = runTest {
        `when`(mockDao.getRatesForBase("USD")).thenReturn(null)
        val response = CurrencyResponse(1.0, "USD", "2023-01-01", mapOf("EUR" to 0.85))
        `when`(mockApi.getLatestRates("USD")).thenReturn(response)

        val result = repository.getRates("USD")
        assertEquals(0.85, result["EUR"]!!, 0.001)
        verify(mockDao).insertRates(any(CurrencyRateEntity::class.java))
    }
}
