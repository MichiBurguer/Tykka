package com.example.tykka.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.booleanPreferencesKey

private val Context.dataStore by preferencesDataStore(name = "user_settings")

class DataStoreManager(private val context: Context) {

    companion object {
        private val CURRENCY_KEY = stringPreferencesKey("currency_symbol")
        private val DAYS_ALERT_KEY = intPreferencesKey("days_alert")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    }

    // Lectura de Preferencias
    val currencySymbol: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CURRENCY_KEY] ?: "$"
    }

    val daysAlert: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[DAYS_ALERT_KEY] ?: 7 // Valor por defecto: 7 días
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[DARK_MODE_KEY] ?: false // Valor por defecto: Modo Claro
    }

    // Escritura de Preferencias
    suspend fun saveCurrencySymbol(symbol: String) {
        context.dataStore.edit { prefs -> prefs[CURRENCY_KEY] = symbol }
    }

    suspend fun saveDaysAlert(days: Int) {
        context.dataStore.edit { prefs -> prefs[DAYS_ALERT_KEY] = days }
    }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[DARK_MODE_KEY] = enabled }
    }
}