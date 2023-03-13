package com.example.statustracker.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("date")
        val CURRENT_DATE = stringPreferencesKey("CURRENT_DATE")
    }

    suspend fun saveCurrentDateFromSheet(date: String) {
        context.dataStore.edit { preference ->
            preference[CURRENT_DATE] = date
        }
    }

    val getCurrentDate: Flow<String?> = context.dataStore.data.map {
        it[CURRENT_DATE] ?: ""
    }
}