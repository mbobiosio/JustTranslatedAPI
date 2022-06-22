package com.mbobiosio.justtranslatedapi.di

import com.mbobiosio.justtranslatedapi.data.remote.repository.TranslationRepositoryImpl
import com.mbobiosio.justtranslatedapi.domain.AuthInterceptor
import com.mbobiosio.justtranslatedapi.domain.repository.TranslationRepository
import com.mbobiosio.justtranslatedapi.domain.usecase.TranslationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor() = AuthInterceptor()

    @Provides
    @Singleton
    fun provideTranslationRepository(
        translationRepositoryImpl: TranslationRepositoryImpl
    ): TranslationRepository = translationRepositoryImpl

    @Provides
    @Singleton
    fun provideTranslationUseCase(
        translationRepository: TranslationRepository
    ): TranslationUseCase = TranslationUseCase(translationRepository)
}
