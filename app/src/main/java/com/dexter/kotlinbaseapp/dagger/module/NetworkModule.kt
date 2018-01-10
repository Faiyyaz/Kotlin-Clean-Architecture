package com.dexter.kotlinbaseapp.dagger.module

import com.dexter.kotlinbaseapp.BuildConfig
import com.dexter.kotlinbaseapp.activities.detail.DetailApiCall
import com.dexter.kotlinbaseapp.activities.search.SearchApiCall
import com.dexter.kotlinbaseapp.api.service.OMDBApiInterface
import com.dexter.kotlinbaseapp.dagger.scopes.PerApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun provideCall(): Retrofit {

        val httpClient = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        httpClient.addInterceptor(loggingInterceptor)
        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    /**
     * add api service & api methods here
     */

    @Provides
    @PerApplication
    fun providesOMDBApiInterface(retrofit: Retrofit) : OMDBApiInterface{
        return retrofit.create(OMDBApiInterface::class.java)
    }

    @Provides
    @PerApplication
    fun providesSearchApiCall(omdbApiInterface: OMDBApiInterface) : SearchApiCall{
        return SearchApiCall(omdbApiInterface)
    }

    @Provides
    @PerApplication
    fun providesDetailApiCall(omdbApiInterface: OMDBApiInterface) : DetailApiCall{
        return DetailApiCall(omdbApiInterface)
    }
}
