package com.pregnancy.edu.di

import android.content.Context
import androidx.work.WorkManager
import com.pregnancy.data.repository.AuthRepositoryImpl
import com.pregnancy.data.repository.BlogRepositoryImpl
import com.pregnancy.data.repository.DashboardRepositoryImpl
import com.pregnancy.data.repository.PregnancyRepositoryImpl
import com.pregnancy.data.repository.ReminderRepositoryImpl
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.data.source.remote.api.AuthApiService
import com.pregnancy.data.source.remote.api.BlogApiService
import com.pregnancy.data.source.remote.api.DashboardApiService
import com.pregnancy.data.source.remote.api.PregnancyApiService
import com.pregnancy.data.source.remote.api.ReminderApiService
import com.pregnancy.domain.repository.AuthRepository
import com.pregnancy.domain.repository.BlogRepository
import com.pregnancy.domain.repository.DashboardRepository
import com.pregnancy.domain.repository.PregnancyRepository
import com.pregnancy.domain.repository.ReminderRepository
import com.pregnancy.domain.usecase.auth.GetMyProfileUseCase
import com.pregnancy.domain.usecase.auth.LoginUseCase
import com.pregnancy.domain.usecase.auth.RegisterUseCase
import com.pregnancy.domain.usecase.auth.SendOtpUseCase
import com.pregnancy.domain.usecase.auth.ValidateEmailUseCase
import com.pregnancy.domain.usecase.blogpost.GetBlogPostUseCase
import com.pregnancy.domain.usecase.blogpost.GetBlogPostsUseCase
import com.pregnancy.domain.usecase.pregnancy.GetGestationalWeekInsightUseCase
import com.pregnancy.domain.usecase.reminder.CancelReminderUseCase
import com.pregnancy.domain.usecase.reminder.GetRemindersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService, tokenManager: TokenManager): AuthRepository {
        return AuthRepositoryImpl(authApiService, tokenManager)
    }

    @Provides
    @Singleton
    fun providePregnancyRepository(pregnancyApiService: PregnancyApiService): PregnancyRepository {
        return PregnancyRepositoryImpl(pregnancyApiService)
    }

    @Provides
    @Singleton
    fun providePregnancyApiService(retrofit: Retrofit): PregnancyApiService {
        return retrofit.create(PregnancyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReminderApi(retrofit: Retrofit): ReminderApiService {
        return retrofit.create(ReminderApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardApi(retrofit: Retrofit): DashboardApiService {
        return retrofit.create(DashboardApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(apiService: DashboardApiService): DashboardRepository {
        return DashboardRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideReminderRepository(apiService: ReminderApiService, workManager: WorkManager): ReminderRepository {
        return ReminderRepositoryImpl(apiService, workManager)
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideBlogApiService(retrofit: Retrofit): BlogApiService {
        return retrofit.create(BlogApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBlogRepository(apiService: BlogApiService): BlogRepository {
        return BlogRepositoryImpl(apiService)
    }
}