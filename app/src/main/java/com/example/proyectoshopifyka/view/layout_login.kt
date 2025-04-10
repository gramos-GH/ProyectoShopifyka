package com.example.proyectoshopifyka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.proyectoshopifyka.databinding.FragmentLayoutLoginBinding
import com.example.proyectoshopifyka.utils.FragmentComunicator
import com.example.proyectoshopifyka.viewModel.SignInViewModel
import androidx.navigation.fragment.findNavController
import androidx.core.widget.addTextChangedListener
import android.widget.Toast



class layout_login : Fragment() {

    private var _binding: FragmentLayoutLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()
    var isValid: Boolean = false
    private lateinit var communicator: FragmentComunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLayoutLoginBinding.inflate(inflater, container, false)
        //communicator = requireActivity() as OnboardingActivity
        setupView()
        //setupObservers()
        return binding.root

    }
    private fun setupView() {
        binding.textView2.setOnClickListener {
            findNavController().navigate(R.id.action_layout_login_to_layout_register)
        }


        binding.filledButton.setOnClickListener {
            if (isValid) {
                //requestLogin()
            } else {
                Toast.makeText(activity, "Ingreso invalido", Toast.LENGTH_SHORT).show()
            }
        }



        binding.etContrasenia.addTextChangedListener {
            if (binding.etContrasenia.text.toString().isEmpty()) {
                binding.tilCorreo.error = "Por favor introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }


        binding.etCorreo.addTextChangedListener {
            if (binding.etCorreo.text.toString().isEmpty()) {
                binding.tilCorreo.error = "Por favor introduce una contraseña"
                isValid = false
            } else {
                isValid = true
            }
        }
    }


}