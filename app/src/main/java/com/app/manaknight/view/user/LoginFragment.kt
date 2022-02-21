package com.app.manaknight.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.manaknight.R
import com.app.manaknight.databinding.FragmentLoginBinding
import com.app.manaknight.databinding.SheetResetBinding
import com.app.manaknight.util.*
import com.app.manaknight.viewmodel.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private var resetView: SheetResetBinding? = null
    private var isVerified = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoginState()
        observeResetData()
        observeCodeData()

        binding.btnLogin.setOnClickListener {
            if (verifyLoginData()) {
                viewModel.loginUser(
                    email = binding.email.text.toString(),
                    password = binding.password.text.toString()
                )
            }
        }

        binding.btnNew.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.resetBtn.setOnClickListener {
            resetSheet()
        }

    }

    private fun observeCodeData() {
        viewModel.validateCodeLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    if (response.data?.code == "ACCOUNT_NOT_VERIFIED") {
                        isVerified = false
                        resetView?.btnReset?.text = getString(R.string.confirm)
                    } else {
                        isVerified = true
                        snackBar(response.data?.message ?: "Success")
                    }
                }
                is Resource.Error -> {
                    binding.loader.hide()
                    snackBar(response.message!!)
                }
                is Resource.Loading -> binding.loader.show()
            }
        }
    }

    private fun resetSheet() {
        val sheet = BottomSheetDialog(requireContext())
        resetView = SheetResetBinding.inflate(layoutInflater)

        resetView!!.btnReset.setOnClickListener {
            if (!resetView!!.resetEmail.isNotValidEmail()) {
                resetView!!.resetEmail.error = "Enter valid email"
            } else {
                if (isVerified)
                    viewModel.resetPass(resetView!!.resetEmail.text.toString())
                else viewModel.verifyUser(resetView!!.resetEmail.text.toString())
            }
        }

        resetView!!.pinCode.afterTextChanged {
            if (it.length == 8) {
                viewModel.validateCode(
                    resetView!!.resetEmail.text.toString(),
                    resetView!!.pinCode.text.toString()
                )
            }
        }

        sheet.setCancelable(true)
        sheet.setContentView(resetView!!.root)
        sheet.show()
    }

    private fun observeLoginState() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.loader.hide()
                    SharedPrefUtils().setValue(
                        SharedPref.USER_TOKEN,
                        response.data?.data?.access_token
                    )
                    SharedPrefUtils().setValue(
                        SharedPref.USER_EMAIL,
                        response.data?.data?.email
                    )
                    SharedPrefUtils().setValue(
                        SharedPref.FIRST_NAME,
                        response.data?.data?.first_name
                    )
                    SharedPrefUtils().setValue(
                        SharedPref.LAST_NAME,
                        response.data?.data?.last_name
                    )
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is Resource.Error -> {
                    binding.loader.hide()
                    snackBar(response.message!!)
                }
                is Resource.Loading -> binding.loader.show()
            }
        }
    }

    private fun observeResetData() {
        viewModel.resetLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    resetView?.loaderSheet?.hide()
                    if (response.data?.code == "ACCOUNT_NOT_VERIFIED") {
                        isVerified = false
                        resetView?.errorText?.text = response.data.message
                        resetView?.errorText?.show()
                        resetView?.btnReset?.text = getString(R.string.get_verification_code)
                    } else {
                        isVerified = true
                        binding.loader.hide()
                        resetView?.errorText?.text = response.data?.message
                        resetView?.errorText?.show()
                        resetView?.btnReset?.text = getString(R.string.confirm)
                        resetView?.pinCode?.show()
                    }
                }
                is Resource.Error -> {
                    resetView?.loaderSheet?.hide()
                    resetView?.errorText?.text = response.data?.message
                    resetView?.errorText?.show()
                }
                is Resource.Loading -> {
                    resetView?.loaderSheet?.show()
                    resetView?.errorText?.hide()
                }

            }
        }
    }

    private fun verifyLoginData(): Boolean {
        return when {
            !binding.email.isNotValidEmail() -> {
                binding.email.error = "Enter valid email address"
                false
            }
            binding.password.checkIsEmpty() -> {
                binding.password.error = "Enter valid password"
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