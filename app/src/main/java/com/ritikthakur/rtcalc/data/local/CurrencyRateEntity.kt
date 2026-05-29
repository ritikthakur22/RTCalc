package com.ritikthakur.rtcalc.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyRateEntity(
    @PrimaryKey
    val baseCurrency: String,
    val ratesJson: String,
    val lastUpdated: Long
)
