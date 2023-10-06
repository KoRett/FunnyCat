package com.korett.funnycat.data.storage

import com.korett.funnycat.data.model.CatRetrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface CatService {

    @GET("v1/images/search")
    suspend fun getCats(@Query("limit") number: Int) : Response<List<CatRetrofit>>

    companion object {
        private const val API_KEY =
            "live_fs1Jm6DpV9OWBEUMUIgG5JTQLOyGPWpiN5wVFQxRGXFZDS3IQTyxNkYtqywmtlUZ"

        fun create(): CatService {

            val clientBuilder = OkHttpClient.Builder()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            clientBuilder.addInterceptor(interceptor)

            clientBuilder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(name = "x-api-key", value = API_KEY)
                    .build()
                return@addInterceptor chain.proceed(request)
            }

            val retrofit = Retrofit.Builder().baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()
            return retrofit.create(CatService::class.java)
        }
    }
}