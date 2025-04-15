package com.example.proyectoshopifyka.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyectoshopifyka.R
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectoshopifyka.databinding.FragmentLayoutRegisterBinding
import com.example.proyectoshopifyka.utils.FragmentComunicator
import com.example.proyectoshopifyka.view.home.HomeActivity
import com.example.proyectoshopifyka.viewModel.SignUpViewModel

class layout_register : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentLayoutRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var communicator: FragmentComunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLayoutRegisterBinding.inflate(inflater, container, false)
        communicator = requireActivity() as OnboardingActivity
        setupView()
        return binding.root

    }

    private fun setupView() {
        binding.filledButton.setOnClickListener {
            viewModel.requestSignUp(binding.etCorreo.text.toString(),
                binding.etContrasenia.text.toString())
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}