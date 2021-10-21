package com.riyazuddin.noteit.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.riyazuddin.noteit.NoteItApplication
import com.riyazuddin.noteit.common.Constants.BASE_URL
import com.riyazuddin.noteit.common.Constants.ENCRYPTED_SHARED_PREF_NAME
import com.riyazuddin.noteit.common.Constants.NOTES_DB_NAME
import com.riyazuddin.noteit.data.local.NoteDao
import com.riyazuddin.noteit.data.local.NotesDB
import com.riyazuddin.noteit.data.remote.NoteItApi
import com.riyazuddin.noteit.data.repository.AuthRepositoryImp
import com.riyazuddin.noteit.data.repository.NoteRepositoryImp
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import com.riyazuddin.noteit.domain.repository.INoteRepository
import com.riyazuddin.noteit.domain.use_cases.add_edit_note.GetNoteUseCase
import com.riyazuddin.noteit.domain.use_cases.notes.DeleteNoteUseCase
import com.riyazuddin.noteit.domain.use_cases.notes.GetNotesUseCase
import com.riyazuddin.noteit.domain.use_cases.notes.InsertNoteUseCase
import com.riyazuddin.noteit.domain.use_cases.notes.NotesUseCases
import com.riyazuddin.noteit.domain.use_cases.signup.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(
        @ApplicationContext context: Context
    ): NoteItApplication = context as NoteItApplication

    @Provides
    @Singleton
    fun provideNoteItApi(): NoteItApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteItApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(noteItApi: NoteItApi): IAuthRepository {
        return AuthRepositoryImp(noteItApi)
    }

    @Provides
    @Singleton
    fun provideNoteDB(
        @ApplicationContext context: Context
    ): NotesDB = Room.databaseBuilder(context, NotesDB::class.java, NOTES_DB_NAME).build()

    @Provides
    @Singleton
    fun provideNoteDao(
        notesDB: NotesDB
    ): NoteDao = notesDB.notedDao()

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao,
        noteItApi: NoteItApi,
        @ApplicationContext context: Context
    ): INoteRepository = NoteRepositoryImp(noteDao, noteItApi, context)

    @Provides
    @Singleton
    fun provideNotesUseCases(notesRepository: INoteRepository) = kotlin.run {
        NotesUseCases(
            getNotes = GetNotesUseCase(notesRepository),
            deleteNoteUseCase = DeleteNoteUseCase(notesRepository),
            insertNoteUseCase = InsertNoteUseCase(notesRepository),
            getNote = GetNoteUseCase(notesRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        authRepository: IAuthRepository,
        sharedPreferences: SharedPreferences
    ) = kotlin.run {
        SignUpUseCase(
            authRepository,
            sharedPreferences
        )
    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
