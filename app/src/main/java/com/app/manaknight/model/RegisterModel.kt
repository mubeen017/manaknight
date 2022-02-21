package com.app.manaknight.model

data class RegisterModel(
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val code: String
)