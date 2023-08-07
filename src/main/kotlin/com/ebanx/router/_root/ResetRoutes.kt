package com.ebanx.router._root

import com.ebanx.service.ResetService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerResetRoutes() {
  routing {
    route("/reset") {
      post {
        ResetService.execute()
        call.respond(HttpStatusCode.OK, "OK")
      }
    }
  }
}