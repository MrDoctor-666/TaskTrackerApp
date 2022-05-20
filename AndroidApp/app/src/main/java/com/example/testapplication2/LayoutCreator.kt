package com.example.testapplication2

import android.content.Context
import android.text.Layout
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testapplication2.models.TaskCompleteModel
import com.example.testapplication2.models.TaskSkipModel

class LayoutCreator(
    private val context: Context
) {

    private val noTasksText = "No tasks for this day"

    fun createDayLayout(date : String, names : List<Task>): LinearLayout {
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
        names.forEach { task ->
            val linearLayoutButtons = LinearLayout(context)
            linearLayoutButtons.orientation = LinearLayout.HORIZONTAL

            val textView2 = TextView(context)
            textView2.text = task.initialTask.getString("taskName")
            textView2.setBackgroundColor(-0x424243 + i)
            i += 30
            textView2.textSize = 16f

            linearLayoutButtons.addView(
                textView2,
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4.0f)
            )

            val buttonComplete = Button(context)
            buttonComplete.text = "Done"
            buttonComplete.setOnClickListener { onReadyButtonClick(task.thisTaskID) }

            if (task.initialTask.getBoolean("canSkip")){
                val buttonSkip = Button(context)
                buttonSkip.text = "Skip"
                buttonSkip.textSize = 10f
                buttonSkip.setOnClickListener { onSkipButtonClick(task.thisTaskID) }

                linearLayoutButtons.addView(
                    buttonSkip,
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
                )

                buttonComplete.textSize = 10f
                linearLayoutButtons.addView(
                    buttonComplete,
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
                )
            }
            else {
                linearLayoutButtons.addView(
                    buttonComplete,
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2.0f)
                )

            }

            linearLayoutVer.addView(
                linearLayoutButtons,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100,
                    1.0f
                )
            )
        }

        return linearLayoutHor
    }

    private fun onReadyButtonClick(id : String){
        val model = TaskCompleteModel()
        model.makePostRequest(id)
    }

    private fun onSkipButtonClick(id : String){
        val model = TaskSkipModel()
        model.makePostRequest(id)
    }

    fun createEmptyDayLayout(date : String) : LinearLayout{

        val linearLayoutHor = LinearLayout(context)
        linearLayoutHor.orientation = LinearLayout.HORIZONTAL
        val textView1 = TextView(context)
        textView1.text = date
        textView1.textSize = 18f
        linearLayoutHor.addView(
            textView1,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        )

        val textView2 = TextView(context)
        textView2.textSize = 18f
        textView2.text = noTasksText
        linearLayoutHor.addView(
            textView2,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4.0f)
        )

        return linearLayoutHor
    }
}