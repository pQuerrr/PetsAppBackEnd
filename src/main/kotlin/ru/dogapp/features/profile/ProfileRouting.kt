package ru.dogapp.features.profile

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import ru.dogapp.features.login.LoginController


fun Application.configureProfileRouting() {
    routing {
        post("/profile") {
            val profileController = ProfileController(call)
            profileController.GettingAProfile()
        }
    }
}