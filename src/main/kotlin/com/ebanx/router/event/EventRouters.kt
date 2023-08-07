package com.ebanx.router.event

import com.ebanx.service.EventService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.registerEventRouters() {
  routing {
    route("/event") {
      post {
        call.respond(HttpStatusCode.Created, EventService.register(call.receive()))
      }
    }
  }
}