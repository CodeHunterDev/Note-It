package com.riyazuddin.noteit.data.remote

import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.data.remote.request.AccountRequest
import com.riyazuddin.noteit.data.remote.request.DeleteNoteRequest
import com.riyazuddin.noteit.data.remote.response.AuthResponse
import com.riyazuddin.noteit.data.remote.response.SimpleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NoteItApi {

    @POST("/api/user/signup")
    suspend fun signUp(
        @Body accountRequest: AccountRequest
    ): Response<AuthResponse>

    @POST("/api/user/login")
    suspend fun login(
        @Body accountRequest: AccountRequest
    ): Response<AuthResponse>

    @POST("/api/note/addNote")
    suspend fun addNote(
        @Body note: Note
    ): Response<SimpleResponse>

    @GET("/api/note/getNotes")
    suspend fun getNotes(): Response<List<Note>>

    @POST("/api/note/deleteNote")
    suspend fun deleteNote(
        @Body deleteNoteRequest: DeleteNoteRequest
    ): Response<SimpleResponse>
}