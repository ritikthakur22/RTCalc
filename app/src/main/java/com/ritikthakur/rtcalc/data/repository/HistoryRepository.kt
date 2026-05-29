package com.ritikthakur.rtcalc.data.repository

import com.ritikthakur.rtcalc.data.local.HistoryDao
import com.ritikthakur.rtcalc.data.local.HistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {
    fun getAllHistory(): Flow<List<HistoryEntity>> = historyDao.getAllHistory()

    suspend fun insertHistory(expression: String, result: String) {
        historyDao.insertHistory(
            HistoryEntity(
                expression = expression,
                result = result
            )
        )
    }

    suspend fun clearHistory() {
        historyDao.clearHistory()
    }
}
