package com.czech.swapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.swapi.repository.SwapiRepository
import com.czech.swapi.repository.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val starRepository = SwapiRepository()

    private val mutableData = MutableLiveData<UiState<Person>>()
    val immutableData: LiveData<UiState<Person>> = mutableData

    fun getPersonDetails(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mutableData.postValue(UiState(isLoading = true))
                val request = starRepository.getPersonById(personId)
                val person = request.body()
                mutableData.postValue(UiState(data = person))

            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Operation failed", e)
                mutableData.postValue(UiState(error = e.message))
            }
        }
    }
}