package com.template.eventplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.template.eventplanner.composescreens.InfoEvent
import com.template.eventplanner.fragments.viewmodels.EventViewModel
import com.template.eventplanner.databinding.FragmentInfoEventBinding

class InfoEventFragment : Fragment() {

    private var _binding: FragmentInfoEventBinding? = null
    private val binding get() = _binding!!
    var id:Long = 0
    companion object {
        fun newInstance() = InfoEventFragment()
    }
    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         id = requireArguments().getLong("id")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoEventBinding.inflate(inflater, container, false)
        binding.composableInfoFragment.setContent {
            InfoEvent(navController = findNavController(), lifecycleOwner = this, id = id)
        }
        return binding.root
    }
}