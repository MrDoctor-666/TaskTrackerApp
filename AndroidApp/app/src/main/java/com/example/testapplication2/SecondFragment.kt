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
import com.example.testapplication2.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    private lateinit var resultsTextView : TextView
    private lateinit var buttonForData: Button
    private lateinit var model: TaskViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


        model =  ViewModelProvider(this).get(TaskViewModel::class.java)
        resultsTextView = binding.textviewSecond
        buttonForData = binding.buttonSave
        val nameObserver = Observer<String> { newName ->
            resultsTextView.text = newName
        }
        model.getInfoPost().observe(this.viewLifecycleOwner, nameObserver)
        buttonForData.setOnClickListener {
            model.makePostRequest()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}