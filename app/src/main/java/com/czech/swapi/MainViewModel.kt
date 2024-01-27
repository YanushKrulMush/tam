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

class MainViewModel : ViewModel() {

    private val starRepository = SwapiRepository()

    private val mutableData = MutableLiveData<UiState<List<Person>>>()
    val immutableData: LiveData<UiState<List<Person>>> = mutableData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mutableData.postValue(UiState(isLoading = true))
                val request = starRepository.getPeople()
                val characters = request.body()?.results
                mutableData.postValue(UiState(data = characters))

            } catch (e: Exception) {
                Log.e("MainViewModel", "Operation failed", e)
                mutableData.postValue(UiState(error = e.message))
            }
        }
    }
}