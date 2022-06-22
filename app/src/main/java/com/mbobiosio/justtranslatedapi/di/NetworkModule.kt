package com.mbobiosio.justtranslatedapi.di

import com.mbobiosio.justtranslatedapi.BuildConfig
import com.mbobiosio.justtranslatedapi.data.remote.api.ApiService
import com.mbobiosio.justtranslatedapi.domain.AuthInterceptor
import com.mbobiosio.justtranslatedapi.domain.ConnectionInterceptor
import com.mbobiosio.justtranslatedapi.util.Constants
import com.mbobiosio.justtranslatedapi.util.network.ConnectionObserver
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideConnectionInterceptor(
        connectionObserver: ConnectionObserver
    ): ConnectionInterceptor =
        ConnectionInterceptor(connectionObserver)

    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        connectionInterceptor: ConnectionInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        addInterceptor(loggingInterceptor)
        addInterceptor(authInterceptor)
        addInterceptor(connectionInterceptor)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            addConverterFactory(MoshiConverterFactory.create(moshi))
        }.build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().apply {
            addLast(KotlinJsonAdapterFactory())
        }.build()

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)
}
