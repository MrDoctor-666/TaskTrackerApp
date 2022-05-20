package com.example.testapplication2.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication2.InternetConnect
import com.example.testapplication2.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskCompleteModel : ViewModel() {

    private var tasksComplete : MutableLiveData<String> = MutableLiveData("Initial value")

    fun getInfo() : LiveData<String> {
        return tasksComplete
    }

    fun makePostRequest(
        id : String
    ){
        tasksComplete.postValue("Waiting")
        val connectManager = InternetConnect()
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                connectManager.postCompleteTask(id)
            } catch(e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<String> -> tasksComplete.postValue("Data Send")
                else -> tasksComplete.postValue("Error")
            }
        }
    }

}