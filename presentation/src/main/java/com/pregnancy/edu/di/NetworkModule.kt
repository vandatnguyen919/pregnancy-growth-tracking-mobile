package com.pregnancy.edu.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.data.source.remote.interceptor.AuthInterceptor
import com.pregnancy.edu.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()

        return Retrofit.Builder()
//            .baseUrl(BuildConfig.BASE_URL)
            .baseUrl("http://192.168.1.92:8080")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {
        override fun write(out: JsonWriter, value: LocalDateTime?) {
            if (value == null) {
                out.nullValue()
            } else {
                out.value(value.toString()) // ISO-8601 format
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun read(input: JsonReader): LocalDateTime? {
            val dateString = input.nextString()
            return if (dateString.isNullOrEmpty()) {
                null
            } else {
                LocalDateTime.parse(dateString)
            }
        }
    }

    class LocalDateAdapter : TypeAdapter<LocalDate>() {
        override fun write(out: JsonWriter, value: LocalDate?) {
            if (value == null) {
                out.nullValue()
            } else {
                out.value(value.toString()) // ISO-8601 format (yyyy-MM-dd)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun read(input: JsonReader): LocalDate? {
            val dateString = input.nextString()
            return if (dateString.isNullOrEmpty()) {
                null
            } else {
                LocalDate.parse(dateString)
            }
        }
    }
}