package com.example.githubrepository.di

import com.example.githubrepository.data.network.IGithubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

const val API_BASE_URL = "https://api.github.com/"
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun providesConvertorFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, convertorFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(convertorFactory)
            .baseUrl(API_BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providesGithubService(retrofit: Retrofit): IGithubService = retrofit.create()
}