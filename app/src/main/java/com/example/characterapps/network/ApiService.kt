package com.example.characterapps.network

import com.example.characterapps.model.Characters
import com.example.characterapps.network.response.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    fun getCharacters(@Query("page") page: Int): Call<CharacterResponse>

    @GET("character/{id}")
    fun getCharacterDetail(@Path("id") id: Int): Call<Characters>
}