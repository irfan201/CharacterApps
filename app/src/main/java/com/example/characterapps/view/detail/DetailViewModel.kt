package com.example.characterapps.view.detail

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.characterapps.model.Characters
import com.example.characterapps.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _characterDetail = MutableLiveData<Characters>()
    val characterDetail: LiveData<Characters> get() = _characterDetail

    fun fetchCharacterDetail(characterId: Int) {
        val apiService = ApiConfig.getApiService()
        apiService.getCharacterDetail(characterId).enqueue(object : Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                if (response.isSuccessful) {
                    _characterDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                Toast.makeText(null, "Failed to fetch character detail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
