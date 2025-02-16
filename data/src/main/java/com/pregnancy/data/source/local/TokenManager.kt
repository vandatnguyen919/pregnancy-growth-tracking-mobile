package com.pregnancy.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "app_preferences")

        private object Keys {
            val ACCESS_TOKEN = stringPreferencesKey("access_token")
            val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
            val TOKEN_EXPIRY = longPreferencesKey("token_expiry")
        }
    }

    /**
     * Save authentication tokens
     */
    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String? = null,
        expiryTime: Long? = System.currentTimeMillis() + 2 * 60 * 60 * 1000L
    ) {
        context.dataStore.edit { preferences ->
            preferences[Keys.ACCESS_TOKEN] = accessToken
            refreshToken?.let { preferences[Keys.REFRESH_TOKEN] = it }
            expiryTime?.let { preferences[Keys.TOKEN_EXPIRY] = it }
        }
    }

    /**
     * Get the stored access token
     * @return access token or null if not found
     */
    suspend fun getAccessToken(): String? {
        return context.dataStore.data.first()[Keys.ACCESS_TOKEN]
    }

    /**
     * Get the stored refresh token
     * @return refresh token or null if not found
     */
    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.first()[Keys.REFRESH_TOKEN]
    }

    /**
     * Get token expiry timestamp
     * @return expiry timestamp or null if not set
     */
    suspend fun getTokenExpiry(): Long? {
        return context.dataStore.data.first()[Keys.TOKEN_EXPIRY]
    }

    /**
     * Check if the access token is expired
     * @return true if token is expired or expiry time is not set
     */
    suspend fun isTokenExpired(): Boolean {
        val expiry = getTokenExpiry() ?: return true
        return System.currentTimeMillis() >= expiry
    }

    /**
     * Clear all stored tokens and related data
     */
    suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(Keys.ACCESS_TOKEN)
            preferences.remove(Keys.REFRESH_TOKEN)
            preferences.remove(Keys.TOKEN_EXPIRY)
        }
    }

    /**
     * Get token as flow for observing changes
     */
    fun getAccessTokenFlow(): Flow<String?> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[Keys.ACCESS_TOKEN]
            }
    }

    /**
     * Check if user is logged in
     * @return true if access token exists and is not expired
     */
    suspend fun isLoggedIn(): Boolean {
        val token = getAccessToken()
        return !token.isNullOrEmpty() && !isTokenExpired()
    }
}