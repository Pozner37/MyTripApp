package com.example.mytrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytrip.databinding.CountryFragmentBinding
import viewModels.CountryFragmentViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CountryFragment : Fragment() {

    private var _binding: CountryFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: CountryFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(this).get(CountryFragmentViewModel::class.java)
        viewModel.setCountry(args.country)
        Log.e("CountryFragment", "country: ${args.country}")
        Log.e("CountryFragment", "viewModel: ${viewModel.getCountry()}")
        _binding = CountryFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_CountryFragment_to_HomeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}