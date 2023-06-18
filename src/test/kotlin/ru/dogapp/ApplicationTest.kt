package ru.dogapp

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import ru.dogapp.plugins.*

class ApplicationTest {
    @TestApp
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("LOL", bodyAsText())
        }
    }
}

annotation class TestApp
