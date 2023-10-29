package com.example.startproject.api

import androidx.lifecycle.MutableLiveData
import com.example.startproject.BuildConfig
import com.example.startproject.repository.SettingRepository
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


open class ApiBase {

    companion object {
        const val HTTP_ERR_MSG = "通信に失敗しました。(%d)"
        const val NETWORK_ERR_CODE = -1
        const val NETWORK_ERR_MSG = "通信に失敗しました。\n電波状況を確認のうえリトライしてください。"
        const val DATA_ERR_MSG = "通信に失敗しました。(-1)"
        const val MAINTENANCE_MSG = "現在メンテナンス中です。しばらくお待ちください。"
    }

    protected var userId: String = SettingRepository().loadUserId() ?: "-"
    protected var session: String = SettingRepository().loadSession() ?: "-"
    protected var fcmToken: String = SettingRepository().loadFcmToken() ?: "-"
    open var baseUrl: String = ""

    sealed class Result<T> {
        data class Success<T>(val code: Int, val data: T?) : Result<T>()
        data class Error<T>(val code: Int, val message: String = "") : Result<T>()
    }

    open class CallbackEx<T>(val data: MutableLiveData<Result<T>>) : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                data.value = Result.Success(response.code(), response.body())
            } else {
                if (response.code() == 503) {
                    data.value = Result.Error(response.code(), MAINTENANCE_MSG)
                } else {
                    data.value = Result.Error(response.code(), HTTP_ERR_MSG.format(response.code()))
                }
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            data.value = Result.Error(NETWORK_ERR_CODE, NETWORK_ERR_MSG)
        }
    }

    protected fun <S> create(serviceClass: Class<S>): S {
        val gson = GsonBuilder().setLenient().serializeNulls().create()

        // create retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpBuilder.build())
            .build()
        return retrofit.create(serviceClass)
    }

    protected fun <S> createNonNull(serviceClass: Class<S>): S {
        val gson = GsonBuilder().setLenient().create()

        // create retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpBuilder.build())
            .build()
        return retrofit.create(serviceClass)
    }

    protected fun <S> createSerial(serviceClass: Class<S>): S {
        // create retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(httpBuilder.build())
            .build()
        return retrofit.create(serviceClass)
    }

    private val httpBuilder: OkHttpClient.Builder
        get() {
            val httpClient = OkHttpClient.Builder()
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
                httpClient.addInterceptor(loggingInterceptor)
            }
            return httpClient
        }

    fun getErrorMessage(code: Int): String {
        return if (code == 503) {
            MAINTENANCE_MSG
        } else {
            HTTP_ERR_MSG.format(code)
        }
    }
}