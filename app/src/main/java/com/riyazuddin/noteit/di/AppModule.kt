package com.riyazuddin.noteit.di

import android.content.Context
import com.riyazuddin.noteit.NoteItApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(
        @ApplicationContext context: Context
    ): NoteItApplication = context as NoteItApplication
}