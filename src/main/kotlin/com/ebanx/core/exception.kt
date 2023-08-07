package com.ebanx.core

import io.ktor.http.*

open class ApplicationException(
  val status: HttpStatusCode = HttpStatusCode.InternalServerError,
  val content: Any? = null,
  cause: Throwable? = null,
) : RuntimeException(cause)

fun throwBadRequest(): Nothing =
  throw ApplicationException(status = HttpStatusCode.BadRequest)

fun throwNotFound(): Nothing =
  throw ApplicationException(status = HttpStatusCode.NotFound, content = 0)