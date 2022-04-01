package com.example.testapplication2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private var info : MutableLiveData<String> = MutableLiveData("Initial value")

    fun getInfo() : LiveData<String> {
        return info
    }

    fun makeAllTaskGetRequest() {
        info.postValue("Waiting")
        val connectManager = InternetConnect()
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                connectManager.getTasksRequest()
            } catch(e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<String> -> info.postValue(result.data)
                else -> info.postValue("Error")
            }
        }
    }

    fun makePostRequest(){

    }
}