package com.example.testapplication2

import org.json.JSONArray
import org.json.JSONObject

class JSONCreator {

    fun testJSON () : String {

        val jsonObject = JSONObject()
        jsonObject.put("id", 33)
        jsonObject.put("taskName:", "TestFromAndroid")
        jsonObject.put("endDate", "2022-04-13")
        jsonObject.put("repeat", 5)

        return jsonObject.toString(1)
    }

    fun testJSON2() : String {
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"taskName\": \"TestFromProgram1\",\n" +
                "  \"endDate\": \"2022-04-1\",\n" +
                "  \"repeat\": 3\n" +
                "}"
    }

    fun create(
        taskName : String,
        endDate : String,
        repeat : Int
    ) : String {
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"taskName\": \"$taskName\",\n" +
                "  \"endDate\": \"$endDate\",\n" +
                "  \"repeat\": $repeat\n" +
                "}"
    }

    fun parseListTasksJSON(json: String) : List<Task> {
        val taskList = mutableListOf<Task>()
        val jsonArr = JSONArray(json)
        for (i in 0 until jsonArr.length())
            taskList.add(parseSingleTaskJSON(jsonArr[i].toString()))

        return taskList.sortedBy { it.date }
    }


    private fun parseSingleTaskJSON(json : String) : Task {
        val jsonObj = JSONObject(json)
        val initialT = jsonObj.getJSONObject("initialTask")

        val prevTaskID : String? = if (jsonObj.isNull("prevTaskID")) null
        else jsonObj.getString("prevTaskID")

        return Task(
            initialT,
            jsonObj.getString("thisTaskID"),
            jsonObj.getString("date"),
            if (jsonObj.isNull("nextTaskID")) null else jsonObj.getString("nextTaskID"),
            prevTaskID
        )
    }

}

class Task(
    val initialTask : JSONObject,
    val thisTaskID : String,
    val date: String,
    val nextTaskID : String?,
    val prevTaskID : String?
){
}