package com.example.testapplication2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private var tasksGet : MutableLiveData<String> = MutableLiveData("Initial value")
    private var infoPost : MutableLiveData<String> = MutableLiveData("Initial value")

    fun getInfo() : LiveData<String> {
        return tasksGet
    }

    fun getInfoPost() : LiveData<String> {
        return infoPost
    }

    fun makeAllTaskGetRequest() {
        tasksGet.postValue("Waiting")
        val connectManager = InternetConnect()
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                connectManager.getTasksRequest()
            } catch(e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<String> -> tasksGet.postValue(result.data)
                else -> tasksGet.postValue("Error")
            }
        }
    }

    fun makePostRequest(
        json : String
    ){
        infoPost.postValue("Waiting")
        val connectManager = InternetConnect()
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                connectManager.postTaskRequest(json)
            } catch(e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<String> -> infoPost.postValue("Data Send")
                else -> infoPost.postValue("Error")
            }
        }
    }
}