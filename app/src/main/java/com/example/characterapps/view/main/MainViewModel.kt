package com.example.characterapps.view.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.characterapps.model.Characters
import com.example.characterapps.network.ApiConfig
import com.example.characterapps.network.response.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _characterList = MutableLiveData<List<Characters>>()
    val characterList: LiveData<List<Characters>> get() = _characterList

    private var currentPage = 1
    var isLastPage = false
    var isLoading = false

    init {
        fetchData()
    }

    fun fetchData() {
        if (!isLoading && !isLastPage) {
            isLoading = true
            val apiService = ApiConfig.getApiService()
            apiService.getCharacters(currentPage).enqueue(object : Callback<CharacterResponse> {
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    if (response.isSuccessful) {
                        val characters = response.body()?.results ?: emptyList()
                        if (characters.isNotEmpty()) {
                            _characterList.value = _characterList.value.orEmpty() + characters
                            currentPage++
                        } else {
                            isLastPage = true
                        }
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    isLoading = false
                    _characterList.value = emptyList()
                    Toast.makeText(null, "Failed to fetch character list", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}
