package com.app.manaknight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.manaknight.model.ErrorBody
import com.app.manaknight.model.RegisterResponse
import com.app.manaknight.model.ResetResponse
import com.app.manaknight.repository.APIRepository
import com.app.manaknight.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val repository = APIRepository()

    val loginLiveData: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val resetLiveData: MutableLiveData<Resource<ResetResponse>> = MutableLiveData()
    val validateCodeLiveData: MutableLiveData<Resource<ResetResponse>> = MutableLiveData()

    fun loginUser(
        email: String,
        password: String
    ) = viewModelScope.launch {
        loginLiveData.postValue(Resource.Loading())
        val response =
            repository.login(
                email = email,
                password = password
            )
        loginLiveData.postValue(handleResponse(response))
    }

    fun resetPass(email: String) = viewModelScope.launch {
        resetLiveData.postValue(Resource.Loading())
        val response = repository.reset(email)
        resetLiveData.postValue(handleResetResponse(response))
    }
    fun verifyUser(email: String) = viewModelScope.launch {
        resetLiveData.postValue(Resource.Loading())
        val response = repository.verifyUser(email)
        resetLiveData.postValue(handleResetResponse(response))
    }

    fun validateCode(email: String, code: String) = viewModelScope.launch {
        validateCodeLiveData.postValue(Resource.Loading())
        val response = repository.validateCode(email, code)
        validateCodeLiveData.postValue(handleCodeResponse(response))
    }

    private fun handleResponse(response: Response<RegisterResponse>): Resource<RegisterResponse> {
        val res = response.body()
        return if (response.isSuccessful && response.code() == 200 && res != null) {
            Resource.Success(res)
        } else {
            val gson = Gson()
            val errorResponse: ErrorBody = gson.fromJson(
                response.errorBody()?.string(),
                ErrorBody::class.java
            )
            Resource.Error(errorResponse.message ?: "Something went wrong")
        }
    }

    private fun handleCodeResponse(response: Response<ResetResponse>): Resource<ResetResponse> {
        val res = response.body()
        return if (response.isSuccessful && res?.success == true)
            Resource.Success(res)
        else {
            val gson = Gson()
            val errorResponse: ErrorBody? = gson.fromJson(
                response.errorBody()?.string(),
                ErrorBody::class.java
            )
            Resource.Error(errorResponse?.message ?: "Something went wrong")
        }
    }

    private fun handleResetResponse(response: Response<ResetResponse>): Resource<ResetResponse> {
        val res = response.body()
        return if (response.isSuccessful && response.code() == 200 && res != null)
            Resource.Success(res)
        else {
            val gson = Gson()
            val errorResponse: ErrorBody = gson.fromJson(
                response.errorBody()?.string(),
                ErrorBody::class.java
            )
            Resource.Error(errorResponse.message ?: "Something went wrong")
        }
    }

}