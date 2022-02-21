package com.app.manaknight.communication

import com.app.manaknight.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("member/api/register")
    @Headers("Accept: application/json")
    suspend fun registerUser(
        @Body register: RegisterModel
    ): Response<RegisterResponse>

    @POST("member/api/login")
    @Headers("Accept: application/json")
    suspend fun loginUser(
        @Body login: LoginModel
    ): Response<RegisterResponse>

    @POST("member/api/forgot")
    @Headers("Accept: application/json")
    suspend fun resetPassword(
        @Body reset: ResetModel
    ): Response<ResetResponse>

    @POST("member/api/verify-account")
    @Headers("Accept: application/json")
    suspend fun verifyAccount(
        @Body reset: ResetModel
    ): Response<ResetResponse>

    @POST("member/api/validate-reset-code")
    @Headers("Accept: application/json")
    suspend fun validateCode(
        @Body reset: CodeModel
    ): Response<ResetResponse>

    @POST("member/api/reset-password")
    @Headers("Accept: application/json")
    suspend fun changePassword(
        @Body reset: PassChangeModel
    ): Response<ResetResponse>


}