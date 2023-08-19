package com.example.practice.di

import android.content.Context
import com.example.practice.data.repository.DatabaseRepositoryImpl
import com.example.practice.data.repository.SharedPreferencesRepositoryImpl
import com.example.practice.data.repository.UnsplashRepositoryImpl
import com.avv2050soft.unsplashtool.domain.repository.DatabaseRepository
import com.avv2050soft.unsplashtool.domain.repository.SharedPreferencesRepository
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabaseRepository(@ApplicationContext context: Context) : DatabaseRepository {
        return DatabaseRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUnsplashRepository() : UnsplashRepository {
        return UnsplashRepositoryImpl()
    }
}