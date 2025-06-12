package com.example.proyectoshopifyka.view.onboarding

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.proyectoshopifyka.R
import com.example.proyectoshopifyka.core.LocationProvider
import com.example.proyectoshopifyka.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.appcompat.app.AlertDialog

@AndroidEntryPoint
class layout_login : Fragment() {
    @Inject lateinit var locationProvider: LocationProvider

    private var _binding: FragmentLayoutLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()
    private lateinit var communicator: FragmentComunicator

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineLocationGranted || coarseLocationGranted) {
            // ✅ Permiso concedido, puedes obtener la ubicación
            getUserLocation()
        } else {
            // ❌ Permiso denegado
            Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        communicator = requireActivity() as FragmentComunicator
        _binding = FragmentLayoutLoginBinding.inflate(inflater, container, false)
        setupView()
        setupObservers()
        return binding.root
    }

    private fun setupView() {
        getUserLocation() // Actions

        binding.textView2.setOnClickListener {
            findNavController().navigate(R.id.action_layout_login_to_layout_register)
        }
        binding.textView.setOnClickListener {
            findNavController().navigate(R.id.action_layout_login_to_restorePassword)
        }

        binding.filledButton.setOnClickListener {
            if (validateInputs()) {
                requestLogin()
            } else {
                val message = if (binding.etCorreo.text.isNullOrEmpty() && binding.etContrasenia.text.isNullOrEmpty()) {
                    "Por favor, ingresa tu correo y contraseña."
                } else if (binding.etCorreo.text.isNullOrEmpty()) {
                    "Por favor, ingresa tu correo."
                } else {
                    "Por favor, ingresa tu contraseña."
                }
                mostrarErrorDialog("Campos obligatorios", message)
            }
        }

        // Limpiar errores del TextInputLayout cuando el usuario empieza a escribir
        binding.etCorreo.addTextChangedListener {
            binding.tilCorreo.error = null // Limpia el error cuando se edita
        }

        binding.etContrasenia.addTextChangedListener {
            binding.tilContrasenia.error = null // Limpia el error cuando se edita
        }
    }

    private fun validateInputs(): Boolean {
        val emailNotEmpty = binding.etCorreo.text.toString().isNotEmpty()
        val passwordNotEmpty = binding.etContrasenia.text.toString().isNotEmpty()

        binding.tilCorreo.error = if (!emailNotEmpty) "Introduce un correo" else null
        binding.tilContrasenia.error = if (!passwordNotEmpty) "Introduce tu contraseña" else null

        return emailNotEmpty && passwordNotEmpty
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
        viewModel.sessionValid.observe(viewLifecycleOwner) { validSession ->
            if (validSession) {
                (activity as? HomeActivity)?.loginSuccess()
                findNavController().navigate(R.id.action_layout_login_to_weatherFragment)
            } else {
                mostrarErrorDialog("Credenciales incorrectas", "El usuario o la contraseña no coinciden.")
            }
        }
    }

    private fun requestLogin() {
        viewModel.requestSignIn(binding.etCorreo.text.toString(), binding.etContrasenia.text.toString())
    }

    fun getUserLocation() {
        if (!hasLocationPermission()) {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
            return
        }

        lifecycleScope.launch {
            try {
                val location = locationProvider.getCurrentLocation()
                location?.let {
                    Log.i("LOCATION", "Ubicación obtenida: ${it.latitude}, ${it.longitude}")
                } ?: run {
                    Log.e("LOCATION", "Error al obtener la ubicación")
                    Toast.makeText(requireContext(), "Error al obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("LOCATION_ERROR", "Error en solicitud de ubicación: ${e.message}")
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Muestra un AlertDialog con un título y un mensaje de error.
     */
    private fun mostrarErrorDialog(titulo: String, mensaje: String) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss() // Cierra el diálogo al presionar Aceptar
                }
                .create()
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}