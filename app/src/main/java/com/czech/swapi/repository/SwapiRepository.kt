package com.czech.swapi.repository

import com.czech.swapi.repository.model.Person
import com.czech.swapi.repository.model.SwapiResponse
import retrofit2.Response

class SwapiRepository {

    suspend fun getPeople(): Response<SwapiResponse> =
        SwapiService.swapiService.getSwapiResponse()

    suspend fun getPersonById(id: Int): Response<Person> =
        SwapiService.swapiService.getPersonById(id)

}