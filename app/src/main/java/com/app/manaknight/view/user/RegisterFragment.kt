package com.app.manaknight.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.manaknight.R
import com.app.manaknight.databinding.FragmentRegisterBinding
import com.app.manaknight.util.*
import com.app.manaknight.util.SharedPref.USER_TOKEN
import com.app.manaknight.viewmodel.RegisterUserViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRegisterData()

        binding.btnSignup.setOnClickListener {
            if (verifyRegisterData()) {
                viewModel.registerUser(
                    email = binding.email.text.toString(),
                    firstName = binding.firstName.text.toString(),
                    lastName = binding.lastName.text.toString(),
                    password = binding.password.text.toString(),
                    code = binding.code.text.toString(),
                )
            }
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun observeRegisterData() {
        viewModel.registerLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.loader.hide()
                    SharedPrefUtils().setValue(USER_TOKEN, response.data?.data?.access_token)
                    snackBar("Account created, Go to login")
                }
                is Resource.Error -> {
                    binding.loader.hide()
                    snackBar(response.message!!)
                }
                is Resource.Loading -> binding.loader.show()
            }
        }
    }

    private fun verifyRegisterData(): Boolean {
        return when {
            binding.firstName.checkIsEmpty() -> {
                binding.firstName.error = "Enter valid first name"
                false
            }
            binding.lastName.checkIsEmpty() -> {
                binding.lastName.error = "Enter valid last name"
                false
            }
            !binding.email.isNotValidEmail() -> {
                binding.email.error = "Enter valid email address"
                false
            }
            binding.password.checkIsEmpty() || binding.password.length() < 6 -> {
                binding.password.error = "Password should at least be 6 digit"
                false
            }
            binding.code.checkIsEmpty() -> {
                binding.code.error = "Enter valid code"
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}