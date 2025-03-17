package com.pregnancy.edu.di

import android.content.Context
import androidx.work.WorkManager
import com.pregnancy.data.repository.AuthRepositoryImpl
import com.pregnancy.data.repository.BlogRepositoryImpl
import com.pregnancy.data.repository.ReminderRepositoryImpl
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.data.source.remote.api.AuthApiService
import com.pregnancy.data.source.remote.api.BlogApiService
import com.pregnancy.data.source.remote.api.ReminderApiService
import com.pregnancy.domain.repository.AuthRepository
import com.pregnancy.domain.repository.BlogRepository
import com.pregnancy.domain.repository.ReminderRepository
import com.pregnancy.domain.usecase.auth.GetMyProfileUseCase
import com.pregnancy.domain.usecase.blogpost.GetBlogPostUseCase
import com.pregnancy.domain.usecase.blogpost.GetBlogPostsUseCase
import com.pregnancy.domain.usecase.auth.LoginUseCase
import com.pregnancy.domain.usecase.auth.RegisterUseCase
import com.pregnancy.domain.usecase.auth.SendOtpUseCase
import com.pregnancy.domain.usecase.auth.ValidateEmailUseCase
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
    fun provideGetBlogPostUseCase(repository: BlogRepository): GetBlogPostUseCase {
        return GetBlogPostUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSendOtpUseCase(authRepository: AuthRepository): SendOtpUseCase {
        return SendOtpUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(authRepository: AuthRepository): ValidateEmailUseCase {
        return ValidateEmailUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetMyProfileUseCase(authRepository: AuthRepository): GetMyProfileUseCase {
        return GetMyProfileUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService, tokenManager: TokenManager): AuthRepository {
        return AuthRepositoryImpl(authApiService, tokenManager)
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
    fun provideReminderRepository(apiService: ReminderApiService, workManager: WorkManager): ReminderRepository {
        return ReminderRepositoryImpl(apiService, workManager)
    }

    @Singleton
    fun provideGetRemindersUseCase(repository: ReminderRepository): GetRemindersUseCase {
        return GetRemindersUseCase(repository)
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

    @Provides
    @Singleton
    fun provideGetBlogPostsUseCase(repository: BlogRepository): GetBlogPostsUseCase {
        return GetBlogPostsUseCase(repository)
    }
}