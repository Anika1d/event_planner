package com.template.eventplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.template.eventplanner.composescreens.ChangeEvent
import com.template.eventplanner.composescreens.CreateEvent
import com.template.eventplanner.databinding.FragmentChangeEventBinding
import com.template.eventplanner.fragments.viewmodels.ChangeEventViewModel
import com.template.eventplanner.ui.theme.EventPlannerTheme

class ChangeEventFragment : Fragment() {
    private var _binding: FragmentChangeEventBinding? = null
    private val binding get() = _binding!!
    var arg: Array<String>? = null

    companion object {
        fun newInstance() = ChangeEventFragment()
    }

    private lateinit var viewModel: ChangeEventViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arg = requireArguments().getStringArray("event")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentChangeEventBinding.inflate(inflater, container, false)
        binding.composableChangeEvent.setContent {
            EventPlannerTheme {
                if (arg == null)
                    CreateEvent(navController = findNavController())
                else if (arg!!.isEmpty())
                    CreateEvent(navController = findNavController())
                else
                    ChangeEvent(event = arg, navController = findNavController())
            }
        }
        return binding.root
    }
}