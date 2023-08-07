package com.ebanx

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
  @Test
  fun `Reset state before starting tests`() = testApplication {
    application { module() }
    
    client.get("/reset").apply {
      assertEquals(HttpStatusCode.OK, status)
      assertEquals("OK", bodyAsText())
    }
  }
  
  @Test
  fun `Get balance for non-existing account`() = testApplication {
    application { module() }
    
    client.get("/balance?account_id=1234").apply {
      assertEquals(HttpStatusCode.NotFound, status)
      assertEquals("0", bodyAsText())
    }
  }
}