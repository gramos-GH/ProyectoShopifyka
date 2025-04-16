package com.example.proyectoshopifyka.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectoshopifyka.R
import com.example.proyectoshopifyka.databinding.FragmentSecondBinding
import com.example.proyectoshopifyka.utils.FragmentComunicator
import com.example.proyectoshopifyka.viewModel.SignInViewModel

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()
    var isValid: Boolean = false
    private lateinit var communicator: FragmentComunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }

    private fun setupView() {
        //actions
        //Validations information user
        binding.btnBoton.setOnClickListener {
            if (isValid) {
                findNavController().navigate(R.id.action_secondFragment_to_firstFragment2)
            } else {
                Toast.makeText(activity, "Registro invalido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etNombre.addTextChangedListener {
            if (binding.etNombre.text.toString().isEmpty()) {
                binding.tilNombre.error = "Por favor introduce tu nombre(s)"
                isValid = false
            } else {
                isValid = true
            }
        }

        binding.etApellidos.addTextChangedListener {
            if (binding.etApellidos.text.toString().isEmpty()) {
                binding.tilApellidos.error = "Por favor introduce tus apellidos"
                isValid = false
            } else {
                isValid = true
            }
        }

        binding.etUsuario.addTextChangedListener {
            if (binding.etUsuario.text.toString().isEmpty()) {
                binding.tilUsuario.error = "Por favor introduce un usuario"
                isValid = false
            } else {
                isValid = true
            }
        }

        binding.etFecha.addTextChangedListener {
            if (binding.etFecha.text.toString().isEmpty()) {
                binding.tilFecha.error = "Por favor introduce una fecha"
                isValid = false
            } else {
                isValid = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}