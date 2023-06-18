package ru.dogapp.features.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseRemote(
    val login:String,
    val email:String,
    val username:String
)

@Serializable
data class ProfileReceiveRemote(
    val token: String
)