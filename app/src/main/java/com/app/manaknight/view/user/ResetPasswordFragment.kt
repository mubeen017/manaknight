package com.app.manaknight.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.manaknight.databinding.FragmentResetPasswordBinding
import com.app.manaknight.util.*
import com.app.manaknight.viewmodel.HomeViewModel

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        binding.email.setText(getUserEmail())

        binding.btnChange.setOnClickListener {
            if (verifyData()) {
                viewModel.changePass(
                    email = binding.email.text.toString(),
                    password = binding.password.text.toString(),
                    code = binding.code.text.toString()
                )
            }
        }

    }

    private fun observeData() {
        viewModel.passLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.loader.hide()
                    snackBar(response.data?.message ?: "Done")
                }
                is Resource.Error -> {
                    binding.loader.hide()
                    snackBar(response.message!!)
                }
                is Resource.Loading -> binding.loader.show()
            }
        }
    }

    private fun verifyData(): Boolean {
        return when {
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