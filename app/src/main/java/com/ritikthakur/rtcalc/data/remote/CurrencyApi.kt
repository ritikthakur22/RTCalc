package com.ritikthakur.rtcalc.data.remote

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class CurrencyResponse(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

interface CurrencyApi {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("from") base: String = "USD"
    ): CurrencyResponse
}
