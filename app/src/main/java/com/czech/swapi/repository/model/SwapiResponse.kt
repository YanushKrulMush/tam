package com.czech.swapi.repository.model

data class SwapiResponse (
    val count: Int,
    val results: List<Person>
)

data class SwapiPersonResponse (
    val count: Int,
    val results: List<Person>
)