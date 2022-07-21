package com.template.core.retrofit

import com.template.core.retrofit.repository.RepositoryRetrofit
import com.template.core.retrofit.repository.RepositoryRetrofitImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule() {
    @Singleton
    @Provides
    fun provideRetrofitModule(retrofit: Retrofit): RetrofitServices =
        retrofit.create(RetrofitServices::class.java)


}

@Module
interface NewsModule {
    @Binds
    fun bindRepository(repository: RepositoryRetrofitImpl): RepositoryRetrofit
}