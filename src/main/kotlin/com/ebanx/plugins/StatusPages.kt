package com.ebanx.plugins

import com.ebanx.core.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
  install(StatusPages) {
    exception<Throwable> { call, cause ->
      when (cause) {
        is ApplicationException ->
          when (cause.content) {
            null ->
              call.respond(cause.status)
            
            else ->
              call.respond(cause.status, cause.content)
          }
        
        else ->
          call.respondText(text = HttpStatusCode.InternalServerError.description, status = HttpStatusCode.InternalServerError)
      }
    }
  }
}