package com.pregnancy.edu.di

import android.content.Context
import com.pregnancy.data.repository.AuthRepositoryImpl
import com.pregnancy.data.repository.BlogRepositoryImpl
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.data.source.remote.api.AuthApi
import com.pregnancy.data.source.remote.api.BlogApiService
import com.pregnancy.domain.repository.AuthRepository
import com.pregnancy.domain.repository.BlogRepository
import com.pregnancy.domain.usecase.GetBlogPostUseCase
import com.pregnancy.domain.usecase.GetBlogPostsUseCase
import com.pregnancy.domain.usecase.LoginUseCase
import com.pregnancy.domain.usecase.RegisterUseCase
import com.pregnancy.domain.usecase.SendOtpUseCase
import com.pregnancy.domain.usecase.ValidateEmailUseCase
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
    fun provideAuthRepository(authApi: AuthApi, tokenManager: TokenManager): AuthRepository {
        return AuthRepositoryImpl(authApi, tokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
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