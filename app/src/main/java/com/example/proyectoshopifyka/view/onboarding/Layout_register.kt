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
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel

class layout_register : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentLayoutRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignUpViewModel>()
    var isValid: Boolean = false
    private lateinit var communicator: FragmentComunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLayoutRegisterBinding.inflate(inflater, container, false)

        val activity = requireActivity()
        Log.d("layout_register", "La actividad es: ${activity::class.java.simpleName}")

        if (activity is HomeActivity) {
            communicator = activity
            setupObservers()
            setupView()
        } else {
            Toast.makeText(context, "La actividad no es la correcta", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun setupView() {
        binding.etCorreo.addTextChangedListener {
            isValid = validateInputs()
        }

        binding.etContrasenia.addTextChangedListener {
            isValid = validateInputs()
        }

        binding.filledButton.setOnClickListener {
            if (isValid) {
                requestsignup()
            } else {
                Toast.makeText(activity, "Registro inválido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.iconButton.setOnClickListener {
            findNavController().navigate(R.id.action_layout_register_to_layout_login)
        }
    }



    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.registrationSuccess.observe(viewLifecycleOwner) { success ->
            Log.d("layout_register", "Estado de registro exitoso")
            if (success) {
                findNavController().navigate(R.id.action_layout_register_to_secondFragment)
            } else {
                Toast.makeText(activity, "Error en el registro, intente nuevamente", Toast.LENGTH_SHORT).show()
            }
        }
        binding.etContrasenia.addTextChangedListener {
            if (binding.etContrasenia.text.toString().isEmpty()) {
                binding.tilContrasenia.error = "Por favor introduce una contraseña"
                isValid = false
            } else {
                isValid = true
            }
        }

        binding.etCorreo.addTextChangedListener {
            if (binding.etCorreo.text.toString().isEmpty()) {
                binding.tilCorreo.error = "Por favor introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailValid = binding.etCorreo.text?.toString()?.let { Patterns.EMAIL_ADDRESS.matcher(binding.etCorreo.text.toString()).matches() } ?: false
        val passwordValid = !binding.etContrasenia.text.isNullOrEmpty()

        return emailValid && passwordValid
    }

    private fun requestsignup() {
        viewModel.requestSignUp(
            binding.etCorreo.text.toString(),
            binding.etContrasenia.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}