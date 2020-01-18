package concept.gifbrowser.di

import com.google.gson.Gson
import concept.gifbrowser.BuildConfig
import concept.gifbrowser.data.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provide API dependencies for [AppComponent]
 */
@Module
class ApiModule {

    private val baseUrl = "https://api.giphy.com/"
    private val connectionTimeout = 30 //in seconds
    private val readTimeout = 30 //in seconds

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
        .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
    }

}