package com.app.manaknight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.manaknight.model.ErrorBody
import com.app.manaknight.model.RegisterResponse
import com.app.manaknight.repository.APIRepository
import com.app.manaknight.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterUserViewModel : ViewModel() {

    private val repository = APIRepository()

    val registerLiveData: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        code: String
    ) = viewModelScope.launch {
        registerLiveData.postValue(Resource.Loading())
        val response =
            repository.signUp(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                code = code
            )
        registerLiveData.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<RegisterResponse>): Resource<RegisterResponse> {
        val res = response.body()
        return if (response.isSuccessful && res != null && res.success) {
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

}