package com.app.manaknight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.manaknight.model.ErrorBody
import com.app.manaknight.model.ResetResponse
import com.app.manaknight.repository.APIRepository
import com.app.manaknight.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val repository = APIRepository()

    val passLiveData: MutableLiveData<Resource<ResetResponse>> = MutableLiveData()

    fun changePass(
        email: String,
        password: String,
        code: String
    ) = viewModelScope.launch {
        passLiveData.postValue(Resource.Loading())
        val response =
            repository.changePass(
                email = email,
                password = password,
                code = code
            )
        passLiveData.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<ResetResponse>): Resource<ResetResponse> {
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