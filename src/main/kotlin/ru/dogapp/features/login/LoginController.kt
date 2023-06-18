package ru.dogapp.features.login

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import ru.dogapp.cache.InMemoryCache
import ru.dogapp.cache.TokenCache
import ru.dogapp.database.tokens.TokenDTO
import ru.dogapp.database.tokens.Tokens
import ru.dogapp.database.users.UserDTO
import ru.dogapp.database.users.Users
import ru.dogapp.features.register.RegisterResponseRemote
import java.util.UUID

class LoginController(
    private val call: ApplicationCall
    ) {
    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)
        println("receive -> $receive, dto -> $userDTO")

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not find")
        } else {
            if (userDTO.password == receive.password){
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(rowId = UUID.randomUUID().toString(), login = receive.login,
                    token = token
                )
                )
                call.respond(LoginResponseRemote(token = token))
            } else  {
                call.respond(HttpStatusCode.BadRequest, "Invalid password!")
            }
        }

    }
}