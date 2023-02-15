package com.griddynamics.gd_testing.di

import com.griddynamics.gd_testing.BuildConfig
import com.griddynamics.gd_testing.data.remote.GithubAPI
import com.griddynamics.gd_testing.data.repository.GithubRepositoryImpl
import com.griddynamics.gd_testing.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): GithubAPI {

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GithubAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: GithubAPI): GithubRepository {
        return GithubRepositoryImpl(api)
    }
}