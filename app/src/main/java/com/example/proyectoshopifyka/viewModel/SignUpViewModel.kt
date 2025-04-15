package com.example.proyectoshopifyka.viewModel

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel: ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean> get() = _loaderState

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> get() = _registrationSuccess

    private val firebase = FirebaseAuth.getInstance()

    fun requestSignUp(email: String, password: String) {
        val trimmedEmail = email.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() || password.isEmpty()) {
            _loaderState.value = false
            _registrationSuccess.value = false
            Log.e("Firebase", "Correo o contraseña inválidos")
            return
        }
        _loaderState.value = true
        viewModelScope.launch {
            try {
                val result = firebase.createUserWithEmailAndPassword(email, password).await()
                _loaderState.value = false
                if (result.user != null) {
                    _registrationSuccess.postValue(true)
                    Log.i("Firebase", "Usuario registrado exitosamente")
                } else {
                    _registrationSuccess.postValue(false)
                    Log.e("Firebase", "Error: No se pudo crear el usuario")
                }
            } catch (e: Exception) {
                _loaderState.value = false
                _registrationSuccess.postValue(false)
                Log.e("Firebase", "Error inesperado")
            }

        }
    }
}