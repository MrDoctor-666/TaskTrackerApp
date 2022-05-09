package com.example.testapplication2

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapplication2.databinding.FragmentFirstBinding
import java.time.LocalDate


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    //private lateinit var analytics: FirebaseAnalytics
    //private lateinit var eventDetails : Bundle

    private lateinit var resultsTextView : TextView
    private lateinit var displayData: Button
    private lateinit var model: TaskViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val testStr = "[{\"initialTask\":{\"thisTaskid\":\"217884b8-a526-4664-9d76-f7162ee1aca1\",\"taskName\":\"TestFromProgram1\",\"endDate\":\"2022-04-01\",\"repeat\":3,\"canSkip\":true,\"repeatDays\":2,\"tag\":null,\"color\":0,\"note\":null},\"thisTaskID\":\"c2\",\"date\":\"2022-04-08\",\"nextTaskID\":\"fc709856-6096-4af1-813d-bd85749caa39\",\"previousTaskID\":null}, {\"initialTask\":{\"thisTaskid\":\"217884b8-a526-4664-9d76-f7162ee1aca1\",\"taskName\":\"TestFromProgram1\",\"endDate\":\"2022-04-01\",\"repeat\":3,\"canSkip\":true,\"repeatDays\":2,\"tag\":null,\"color\":0,\"note\":null},\"thisTaskID\":\"cf2f8239-f251-47c6-beb9-2a495ba39b72\",\"date\":\"2022-04-01\",\"nextTaskID\":\"fc709856-6096-4af1-813d-bd85749caa39\",\"previousTaskID\":null}]"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //analytics = FirebaseAnalytics.getInstance(this.requireContext())

        binding.buttonFirst.setOnClickListener {
            // Create a Bundle containing information about
            // the analytics event
            //eventDetails = Bundle();
            //eventDetails.putString("my_message", "Clicked that button");
            //analytics.logEvent("test_button_click", eventDetails);

            binding.checkBoxFirst.isChecked = true
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        model =  ViewModelProvider(this).get(TaskViewModel::class.java)
        resultsTextView = binding.textviewFirstName
        displayData = binding.buttonGet
        val nameObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            resultsTextView.text = newName
            tasksDisplay(newName)
        }
        model.getInfo().observe(this.viewLifecycleOwner, nameObserver)
        displayData.setOnClickListener {
            model.makeAllTaskGetRequest()
        }

        tasksDisplay(testStr)
    }

    private fun tasksDisplay(tasksString: String){

        if (!JSONCreator().checkIfJSON(tasksString)) return

        binding.parentLayout.removeAllViews()
        val lc = LayoutCreator(this.requireContext())

        val a = JSONCreator().parseListTasksJSON(tasksString) //all tasks
        var curDate = LocalDate.parse(a[0].date)
        var i = 0
        val tempList = mutableListOf<Task>()

        while(i < a.count()){
            when {
                curDate.toString() == a[i].date -> { tempList.add(a[i]); i++}//
                a.find { it.date == curDate.toString() } == null -> { //если такой даты нет
                    binding.parentLayout.addView(lc.createEmptyDayLayout(curDate.toString()))
                    curDate = curDate.plusDays(1)
                }
                else -> { //если такая дата есть, но элементы закончились
                    binding.parentLayout.addView(lc.createDayLayout(curDate.toString(), tempList))
                    curDate = curDate.plusDays(1)
                    tempList.clear()
                    tempList.add(a[i])
                    i++
                }
            }

        }

        while (curDate.toString() != a[i - 1].date) {
            binding.parentLayout.addView(lc.createEmptyDayLayout(curDate.toString()))
            curDate = curDate.plusDays(1)
        }
        binding.parentLayout.addView(lc.createDayLayout(curDate.toString(), tempList))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}