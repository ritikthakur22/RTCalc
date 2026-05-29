package com.ritikthakur.rtcalc.data.repository

import com.ritikthakur.rtcalc.data.local.CurrencyDao
import com.ritikthakur.rtcalc.data.local.CurrencyRateEntity
import com.ritikthakur.rtcalc.data.remote.CurrencyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
    private val dao: CurrencyDao
) {
    private val cacheExpirationMs = 3600_000L // 1 hour

    suspend fun getRates(base: String = "USD"): Map<String, Double> = withContext(Dispatchers.IO) {
        val cached = dao.getRatesForBase(base)
        val now = System.currentTimeMillis()

        if (cached != null && (now - cached.lastUpdated) < cacheExpirationMs) {
            try {
                return@withContext Json.decodeFromString<Map<String, Double>>(cached.ratesJson)
            } catch (e: Exception) {
                // Fallback to fetch
            }
        }

        try {
            val response = api.getLatestRates(base)
            val rates = response.rates.toMutableMap()
            rates[base] = 1.0 // include base currency itself
            
            val ratesJson = Json.encodeToString(rates)
            dao.insertRates(CurrencyRateEntity(base, ratesJson, now))
            
            rates
        } catch (e: Exception) {
            // If offline and api fails, return cached if exists, else throw
            if (cached != null) {
                Json.decodeFromString<Map<String, Double>>(cached.ratesJson)
            } else {
                throw Exception("Failed to fetch rates and no offline cache available.")
            }
        }
    }
}
