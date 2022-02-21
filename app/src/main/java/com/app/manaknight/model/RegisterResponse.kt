package com.app.manaknight.model

data class RegisterResponse(
    val `data`: Data,
    val success: Boolean
) {
    data class Data(
        val access_token: String,
        val email: String,
        val first_name: String,
        val image: String,
        val last_name: String,
        val refresh_token: String,
        val user_id: Int
    )
}