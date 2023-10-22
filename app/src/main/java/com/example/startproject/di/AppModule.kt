package com.example.startproject.di

import com.example.startproject.BuildConfig
import com.example.startproject.data.Endpoints
import com.example.startproject.data.GithubApi
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .method(original.method, original.body)
                    .build()

                return@Interceptor chain.proceed(request)
            })
            .readTimeout(60, TimeUnit.SECONDS)

        // log interceptor
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(loggingInterceptor)
        }
        okHttpBuilder.build()
    }

    single {
        val gson = GsonBuilder().setLenient().serializeNulls().create()
        Retrofit.Builder().baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(GithubApi::class.java) }
}

val appModules = listOf(
    appModule,
    repositoryModule,
    viewModelModule
)