package com.example.proyectoshopifyka.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.proyectoshopifyka.R
import com.example.proyectoshopifyka.view.home.HomeActivity


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

        if (requireActivity() is FragmentComunicator) {
            communicator = requireActivity() as FragmentComunicator
        } else {
            throw IllegalStateException("La actividad que hospeda este fragmento debe implementar FragmentComunicator")
        }

        setupView()
        setupObservers()
        return binding.root

    }

    private fun setupView() {
        //actions
        binding.textView2.setOnClickListener {
            findNavController().navigate(R.id.action_layout_login_to_layout_register)
        }
        binding.textView.setOnClickListener {
            findNavController().navigate(R.id.action_layout_login_to_restorePassword)
        }

        //Validations information user
        binding.filledButton.setOnClickListener {
            if (isValid) {
                requestLogin()
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

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
        viewModel.sessionValid.observe(viewLifecycleOwner) { validSession ->
            if (validSession) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(activity, "Ingreso invalido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLogin() {
        viewModel.requestSignIn(binding.etCorreo.text.toString(),
            binding.etContrasenia.text.toString())
    }
}