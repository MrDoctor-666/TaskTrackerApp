package com.example.testapplication2

import android.app.DatePickerDialog
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
import com.example.testapplication2.models.TaskViewModel
import java.time.LocalDate
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    private lateinit var resultsTextView : TextView
    private lateinit var buttonForData: Button
    private lateinit var model: TaskViewModel
    private lateinit var date: DatePickerDialog
    private var localDate = LocalDate.now()
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
        //resultsTextView = binding.textviewSecond
        //buttonForData = binding.buttonSave
        val nameObserver = Observer<String> { newName ->
            binding.textviewSecond.text = newName
        }
        model.getInfoPost().observe(this.viewLifecycleOwner, nameObserver)
        binding.buttonSave.setOnClickListener {
            model.makePostRequest(JSONCreator().create(
                binding.editTextTaskName.text.toString(),
                localDate.toString(),
                binding.editRepeatDecimal.text.toString().toInt(),
                binding.editRepeatDays.text.toString().toInt(),
                binding.canSkipCheckBox.isChecked
            ))
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener {_, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            localDate = LocalDate.of(year, monthOfYear+1, dayOfMonth)
            binding.datePickerButton.text = localDate.toString()
        }

        binding.datePickerButton.setOnClickListener{
            DatePickerDialog(this.requireContext(),
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}