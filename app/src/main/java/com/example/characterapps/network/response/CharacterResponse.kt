package com.example.characterapps.network.response

import com.example.characterapps.model.Characters

data class CharacterResponse(
    val info: Info,
    val results: List<Characters>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
