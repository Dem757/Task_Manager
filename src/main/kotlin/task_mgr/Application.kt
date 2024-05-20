package task_mgr

import task_mgr.plugins.*
import task_mgr.dao.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import task_mgr.plugins.configureRouting


fun main(args: Array<String>) : Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    DatabaseSingleton.init()
    configureSecurity()
    configureRouting()
}
