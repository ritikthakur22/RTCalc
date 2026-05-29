package com.ritikthakur.rtcalc.di

import android.content.Context
import androidx.room.Room
import com.ritikthakur.rtcalc.data.local.HistoryDao
import com.ritikthakur.rtcalc.data.local.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase(
        @ApplicationContext context: Context
    ): HistoryDatabase {
        return Room.databaseBuilder(
            context,
            HistoryDatabase::class.java,
            "rtcalc_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(database: HistoryDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: HistoryDatabase): com.ritikthakur.rtcalc.data.local.CurrencyDao {
        return database.currencyDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(): com.ritikthakur.rtcalc.data.remote.CurrencyApi {
        val contentType = "application/json".toMediaType()
        val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
        
        return retrofit2.Retrofit.Builder()
            .baseUrl("https://api.frankfurter.app/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(com.ritikthakur.rtcalc.data.remote.CurrencyApi::class.java)
    }
}
