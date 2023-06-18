package ru.dogapp.features.register

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import ru.dogapp.cache.InMemoryCache
import ru.dogapp.cache.TokenCache
import ru.dogapp.features.login.LoginReceiveRemote
import ru.dogapp.features.login.LoginResponseRemote
import ru.dogapp.utils.isValidEmail
import java.util.UUID

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
            val receive = call.receive<RegisterReceiveRemote>()
            if (!receive.email.isValidEmail()){
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            }
        }
    }
}