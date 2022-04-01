package com.example.testapplication2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapplication2.databinding.FragmentFirstBinding

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


            binding.checkBoxFirst.isChecked = true;
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        model =  ViewModelProvider(this).get(TaskViewModel::class.java)
        resultsTextView = binding.textviewFirst
        displayData = binding.buttonGet
        val nameObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            resultsTextView.text = newName
        }
        model.getInfo().observe(this.viewLifecycleOwner, nameObserver)
        displayData.setOnClickListener {
            model.makeAllTaskGetRequest()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}