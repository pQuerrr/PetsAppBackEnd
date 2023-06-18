package ru.dogapp.features.profile

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.dogapp.cache.InMemoryCache
import ru.dogapp.cache.TokenCache
import ru.dogapp.database.tokens.TokenDTO
import ru.dogapp.database.tokens.Tokens
import ru.dogapp.database.users.UserDTO
import ru.dogapp.database.users.Users
import ru.dogapp.utils.isValidEmail
import java.util.UUID

class ProfileController(
    val call: ApplicationCall
) {
    suspend fun GettingAProfile() {
        val receive = call.receive<ProfileReceiveRemote>()
        val tokenDTO = Tokens.fetchTokens(receive.token)
        println("receive -> $receive, dto -> $tokenDTO")
        if (tokenDTO != null) {
            val userDTO = Users.fetchUser(tokenDTO.login)
            if (userDTO != null){
                val username = userDTO.username
                val login = userDTO.login
                val email = userDTO.email
                println("username -> $username, login -> $login, email -> $email")
                call.respond(ProfileResponseRemote(login, email, username))
            }
        }
//        if (tokenDTO == null) {
//            call.respond(HttpStatusCode.BadRequest, "User not find")
//        } else {
//            val userDTO = Users.fetchUser(tokenDTO.login)
//
//        }

    }
}


