package ru.dogapp.features.login

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
import java.util.UUID

fun Application.configureLoginRouting() {

    routing {
        post("/login") {
           val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}
