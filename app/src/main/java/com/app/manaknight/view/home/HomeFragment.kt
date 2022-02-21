package com.app.manaknight.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.manaknight.R
import com.app.manaknight.databinding.FragmentHomeBinding
import com.app.manaknight.util.SharedPrefUtils
import com.app.manaknight.util.userFirstName
import com.app.manaknight.util.userLastName

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvWelcome.text = getString(R.string.welcome, userFirstName(), userLastName())

        binding.btnLogout.setOnClickListener {
            SharedPrefUtils().clearLoginPrefs()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

}