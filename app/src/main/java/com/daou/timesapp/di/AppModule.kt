package com.daou.timesapp.di

import androidx.room.Room
import com.daou.timesapp.BuildConfig
import com.daou.timesapp.data.local.AppDatabase
import com.daou.timesapp.data.NewsRepository
import com.daou.timesapp.data.remote.TimesApi
import com.daou.timesapp.domain.GetClippedArticlesUseCase
import com.daou.timesapp.domain.SearchNewsUseCase
import com.daou.timesapp.ui.clip.ClipViewModel
import com.daou.timesapp.ui.home.HomeViewModel
import com.daou.timesapp.ui.main.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.nytimes.com/"

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ClipViewModel(get(), get()) }
}

val useCaseModule = module {
    factory { SearchNewsUseCase(get()) }
    factory { GetClippedArticlesUseCase(get()) }
}

val networkModule = module {
    factory { provideRetrofit(get()) }
    factory { providesOkHttpClient(get() as Interceptor) }
    factory { keyHeaderInterceptor() }
    factory { provideTimesApi(get()) }
}

val repositoryModule = module {
    single { NewsRepository(get(), get(), get()) }
}

val roomModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "article.db").build()
    }
    single { get<AppDatabase>().clipArticleDao() }
    single { get<AppDatabase>().searchWordDao() }
}

private fun provideTimesApi(retrofit: Retrofit) = retrofit.create(TimesApi::class.java)

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun keyHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val originRequest = chain.request()
        val builder = originRequest.url.newBuilder()
            .addQueryParameter("api-key", BuildConfig.API_KEY).build()
        val newBuilder = originRequest.newBuilder().url(builder).build()
        chain.proceed(newBuilder)
    }
}

fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(interceptor)
        .build()
}

val appModules = listOf(viewModelModule, useCaseModule, networkModule, repositoryModule, roomModule)
