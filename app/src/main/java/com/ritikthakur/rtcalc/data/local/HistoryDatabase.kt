package com.ritikthakur.rtcalc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class, CurrencyRateEntity::class], version = 2, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun currencyDao(): CurrencyDao
}
