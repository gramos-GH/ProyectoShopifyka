package com.example.proyectoshopifyka.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.proyectoshopifyka.databinding.FragmentWeatherBinding
import com.example.proyectoshopifyka.view.home.viewModel.WeatherFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherFragmentViewModel by viewModels() // ✅ Corrección

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observador del clima
        viewModel.weatherInfo.observe(viewLifecycleOwner) { weather ->
            //binding.textCiudad.text = weather.
            binding.textSemana.text = "${weather.lastupdated}"
            binding.textTemp.text = "${weather.tempc} °C"
            //binding.textSaludo.text = "${weather.}"
            //binding.textNumSunset.text = weather.lastupdated
            binding.textNumWind.text =  "${weather.windkph} km/h"
            binding.textNumTemperatura.text = "${weather.feelslikec}°C"

            Glide.with(this)
                .load("https:${weather.condition.icon}")
                .into(binding.imgClima)

        }

        // Llama a la API obteniendo la ubicación actual
        viewModel.fetchWeather("b01d6b51a0bf40c282f15334252104")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
