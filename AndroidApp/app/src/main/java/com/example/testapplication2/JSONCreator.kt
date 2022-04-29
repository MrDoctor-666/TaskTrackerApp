package com.example.testapplication2

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
}