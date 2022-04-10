package com.example.testapplication2

import org.json.JSONObject

class jsonCreator {

    fun testJSON () : String {
        val jsonObject = JSONObject()
        jsonObject.put("id", 33)
        jsonObject.put("taskName:", "TestFromAndroid")
        jsonObject.put("endDate", "2022-04-13")
        jsonObject.put("repeat", 5)

        return jsonObject.toString()
    }

    fun testJSON2() : String {
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"taskName\": \"TestFromProgram1\",\n" +
                "  \"endDate\": \"2022-04-1\",\n" +
                "  \"repeat\": 3\n" +
                "}"
    }
}