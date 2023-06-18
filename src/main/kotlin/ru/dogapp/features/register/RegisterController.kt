package ru.dogapp.features.register

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

class RegisterController(
    val call: ApplicationCall
    ) {
    suspend fun registerNewUser(){
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()){
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDTO = Users.fetchUser(registerReceiveRemote.login)


        if (userDTO != null){
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }else{
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                UserDTO(
                    login = registerReceiveRemote.login,
                    password = registerReceiveRemote.password,
                    email = registerReceiveRemote.email,
                    username = registerReceiveRemote.username
                )
                )

            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }
            catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
        }
            Tokens.insert(TokenDTO(
                rowId = UUID.randomUUID().toString(), login = registerReceiveRemote.login,
                token = token
            )
            )
            call.respond(RegisterResponseRemote(token = token))
        }

    }
}