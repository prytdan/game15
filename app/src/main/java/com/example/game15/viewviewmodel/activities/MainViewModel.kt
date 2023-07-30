package com.example.game15.viewviewmodel.activities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game15.model.internet.Data
import com.example.game15.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MainViewModel(private val repository: Repository) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    val myResponse: MutableLiveData<Response<Data>> = MutableLiveData()

    fun getData(): Boolean {
        var isSuccessful = false
        viewModelScope.launch {
            val response = try {
                repository.getData()
            } catch (e: IOException) {
                Log.d(TAG, "IOException: ${e.message}")
                return@launch
            } catch (e: HttpException) {
                Log.d(TAG, "HttpException: ${e.message}")
                return@launch
            }
            isSuccessful = true
            myResponse.value = response
        }
        return isSuccessful
    }


    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _isLoading.value = false
        }
    }

}