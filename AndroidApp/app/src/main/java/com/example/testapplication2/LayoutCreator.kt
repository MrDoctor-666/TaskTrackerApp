package com.example.testapplication2

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView

class LayoutCreator(
    private val context: Context
) {


    fun createDayLayout(date : String, names : List<String>): LinearLayout {
        val linearLayoutHor = LinearLayout(context)
        linearLayoutHor.orientation = LinearLayout.HORIZONTAL

        val linearLayoutVer = LinearLayout(context)
        linearLayoutVer.orientation = LinearLayout.VERTICAL

        //date
        val textView1 = TextView(context)
        textView1.text = date
        textView1.textSize = 18f
        linearLayoutHor.addView(
            textView1,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        )

        linearLayoutHor.addView(
            linearLayoutVer,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4.0f)
        )

        var i = 0
        names.forEach {
            val textView2 = TextView(context)
            textView2.text = it
            textView2.setBackgroundColor(-0x424243 + i)
            i += 30
            textView2.textSize = 16f

            linearLayoutVer.addView(
                textView2,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100,
                    1.0f
                )
            )
        }

        return linearLayoutHor
    }
}