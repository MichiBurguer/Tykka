package com.example.tykka.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val CURRENCY_KEY = stringPreferencesKey("currency_symbol")
        val DAYS_ALERT_KEY = intPreferencesKey("days_alert_expiration")
    }

    // Leer la moneda configurada (por defecto "$")
    val currencySymbol: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[CURRENCY_KEY] ?: "$"
    }

    // Guardar la moneda
    suspend fun saveCurrencySymbol(symbol: String) {
        context.dataStore.edit { preferences ->
            preferences[CURRENCY_KEY] = symbol
        }
    }

    // Leer días de anticipación para alerta (por defecto 30 días)
    val daysAlert: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[DAYS_ALERT_KEY] ?: 30
    }

    // Guardar días de aviso
    suspend fun saveDaysAlert(days: Int) {
        context.dataStore.edit { preferences ->
            preferences[DAYS_ALERT_KEY] = days
        }
    }
}