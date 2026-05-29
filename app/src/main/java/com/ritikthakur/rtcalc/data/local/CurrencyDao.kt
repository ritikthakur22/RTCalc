package com.ritikthakur.rtcalc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency_rates WHERE baseCurrency = :base")
    suspend fun getRatesForBase(base: String): CurrencyRateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(entity: CurrencyRateEntity)
}
