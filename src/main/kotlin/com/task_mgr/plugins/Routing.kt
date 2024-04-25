package com.task_mgr.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
//        staticFiles("/", File("files")) {
//            default("index.html")
//            preCompressed(CompressedFileType.GZIP)
//        }
//        singlePageApplication {
//            useResources = true
//            filesPath = "web-app"
//            defaultPage = "index.html"
//            ignoreFiles { it.endsWith(".txt") }
//        }
//        staticFiles("/task-board", File("files")) {
//            default("task-board.html")
//            preCompressed(CompressedFileType.GZIP)
//        }
        singlePageApplication {
            react("react-app")
        }
    }
}
