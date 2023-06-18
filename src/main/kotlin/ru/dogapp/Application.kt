package ru.dogapp

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import ru.dogapp.features.login.configureLoginRouting
import ru.dogapp.features.profile.configureProfileRouting
import ru.dogapp.features.register.configureRegisterRouting
import ru.dogapp.plugins.*

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/PetsApp", "org.postgresql.Driver","postgres","z1x2c3v4")
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureSerialization()
    configureProfileRouting()
}
