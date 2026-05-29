package com.ritikthakur.rtcalc.data.remote

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class CurrencyResponse(
    val result: String,
    val base_code: String,
    val rates: Map<String, Double>
)

interface CurrencyApi {
    @GET("latest/{base}")
    suspend fun getLatestRates(
        @retrofit2.http.Path("base") base: String = "USD"
    ): CurrencyResponse
}
