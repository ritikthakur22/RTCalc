package com.ritikthakur.rtcalc.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

enum class AngleMode {
    DEGREE, RADIAN, GRADIAN
}

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val HAPTIC_ENABLED = booleanPreferencesKey("haptic_enabled")
        val ANGLE_MODE = stringPreferencesKey("angle_mode")
        val DECIMAL_PRECISION = intPreferencesKey("decimal_precision")
        val SCIENTIFIC_NOTATION = booleanPreferencesKey("scientific_notation")
    }

    val themeModeFlow: Flow<ThemeMode> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val modeStr = preferences[PreferencesKeys.THEME_MODE] ?: ThemeMode.SYSTEM.name
            try {
                ThemeMode.valueOf(modeStr)
            } catch (e: IllegalArgumentException) {
                ThemeMode.SYSTEM
            }
        }

    val hapticEnabledFlow: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.HAPTIC_ENABLED] ?: true
        }

    val angleModeFlow: Flow<AngleMode> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val modeStr = preferences[PreferencesKeys.ANGLE_MODE] ?: AngleMode.DEGREE.name
            try {
                AngleMode.valueOf(modeStr)
            } catch (e: IllegalArgumentException) {
                AngleMode.DEGREE
            }
        }

    val decimalPrecisionFlow: Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.DECIMAL_PRECISION] ?: 10
        }

    val scientificNotationFlow: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.SCIENTIFIC_NOTATION] ?: false
        }

    suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = mode.name
        }
    }

    suspend fun setHapticEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HAPTIC_ENABLED] = enabled
        }
    }

    suspend fun setAngleMode(mode: AngleMode) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ANGLE_MODE] = mode.name
        }
    }

    suspend fun setDecimalPrecision(precision: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DECIMAL_PRECISION] = precision
        }
    }

    suspend fun setScientificNotation(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCIENTIFIC_NOTATION] = enabled
        }
    }
}
