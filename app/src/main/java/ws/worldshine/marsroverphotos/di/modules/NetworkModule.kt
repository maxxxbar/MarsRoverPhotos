package ws.worldshine.marsroverphotos.di.modules

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ws.worldshine.marsroverphotos.network.NasaApi
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val baseUrl: String =
            "https://api.nasa.gov/mars-photos/api/v1/"
        private const val API_KEY_KEY = "api_key"
        private const val API_KEY_VALUE = "DEMO_KEY"

    }

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit): NasaApi =
        retrofit.create(NasaApi::class.java)

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        interceptors.forEach {
            okHttpClientBuilder.addInterceptor(it)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor.invoke { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(
                    API_KEY_KEY,
                    API_KEY_VALUE
                )
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)
            chain.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    fun providesInterceptors(interceptor: Interceptor?): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        if (interceptor != null) {
            interceptors.add(interceptor)
        }
        interceptors.add(loggingInterceptor)
        return interceptors
    }
}