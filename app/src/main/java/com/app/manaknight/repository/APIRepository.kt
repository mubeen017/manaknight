package com.app.manaknight.repository

import com.app.manaknight.model.*
import com.app.rofoof.communication.APIClient

class APIRepository {

    suspend fun signUp(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        code: String
    ) = APIClient.API.registerUser(
        RegisterModel(
            email = email,
            first_name = firstName,
            last_name = lastName,
            password = password,
            code = code
        )
    )

    suspend fun login(
        email: String,
        password: String
    ) = APIClient.API.loginUser(
        LoginModel(
            email = email,
            password = password
        )
    )

    suspend fun reset(
        email: String
    ) = APIClient.API.resetPassword(ResetModel(email))

    suspend fun verifyUser(
        email: String
    ) = APIClient.API.verifyAccount(ResetModel(email))

    suspend fun validateCode(
        email: String,
        code: String
    ) = APIClient.API.validateCode(CodeModel(email = email, code = code))

    suspend fun changePass(
        email: String,
        code: String,
        password: String
    ) = APIClient.API.changePassword(
        PassChangeModel(
            email = email,
            code = code,
            password = password
        )
    )


}