package com.example.testapplication2

import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class InternetConnect() {
    //TODO something with urls
    //TODO delete android:usesCleartextTraffic="true" and find some other way
    //this one is not safe??
    private val taskURL = "http://192.168.56.1:8080/"
    private val taskURL2 = "http://192.168.0.101:8080/"
    /*fun makeAllTaskGetRequest(
        jsonBody: String
    ): Result<String> {
        val url = URL(loginUrl)
        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.write(jsonBody.toByteArray())
            return Result.Success(inputStream.toString())
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }*/

    fun getTasksRequest() : Result<String> {
        val url = URL(taskURL2)
        val urlCon = url.openConnection() as HttpURLConnection
        urlCon.setRequestProperty("Accept", "application/json")
        val responseCode = urlCon.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = urlCon.inputStream.bufferedReader().use { it.readText() }
            return Result.Success(response)
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }

    fun postTaskRequest(toSend : String) : Result<String>{
        val url = URL(taskURL2)
        val urlCon = url.openConnection() as HttpURLConnection
        urlCon.requestMethod = "POST"
        urlCon.setRequestProperty("Content-Type", "application/json")
        urlCon.setRequestProperty("Accept", "application/json")

        val outputStreamWriter = OutputStreamWriter(urlCon.outputStream)
        outputStreamWriter.write(toSend)
        outputStreamWriter.flush()

        val responseCode = urlCon.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = urlCon.inputStream.bufferedReader().use { it.readText() }
            return Result.Success(response)
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}