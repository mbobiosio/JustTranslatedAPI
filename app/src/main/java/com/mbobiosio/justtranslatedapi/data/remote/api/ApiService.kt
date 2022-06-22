package com.mbobiosio.justtranslatedapi.data.remote.api

import com.mbobiosio.justtranslatedapi.data.remote.model.TranslationDTO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
interface ApiService {
    @GET("/")
    suspend fun translate(
        @Query("lang") language: String,
        @Query("text") text: String
    ): TranslationDTO
}
