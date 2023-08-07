package com.ebanx

import com.ebanx.service.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import kotlin.test.*
import kotlin.test.Test

@TestMethodOrder(OrderAnnotation::class)
class ApplicationTest {
  @Test
  @Order(1)
  fun `Reset state before starting tests`() = testApplication {
    application { module() }
    
    client.get("/reset").apply {
      assertEquals(HttpStatusCode.OK, status)
      assertEquals("OK", bodyAsText())
    }
  }
  
  @Test
  @Order(2)
  fun `Get balance for non-existing account`() = testApplication {
    application { module() }
    
    client.get("/balance?account_id=1234").apply {
      assertEquals(HttpStatusCode.NotFound, status)
      assertEquals("0", bodyAsText())
    }
  }
  
  @Test
  @Order(3)
  fun `Create account with initial balance`() = testApplication {
    application { module() }
    
    val client = createClient {
      install(ContentNegotiation) {
        json()
      }
    }
    
    client.post("/event") {
      contentType(ContentType.Application.Json)
      setBody(
        EventModel(
          type = EventType.Deposit,
          destination = "100",
          amount = 10L
        )
      )
    }.apply {
      assertEquals(HttpStatusCode.Created, status)
      assertEquals("{\"destination\":{\"id\":\"100\",\"balance\":10}}", bodyAsText())
    }
  }
  
  @Test
  @Order(4)
  fun `Deposit into existing account`() = testApplication {
    application { module() }
    
    val client = createClient {
      install(ContentNegotiation) {
        json()
      }
    }
    
    client.post("/event") {
      contentType(ContentType.Application.Json)
      setBody(
        EventModel(
          type = EventType.Deposit,
          destination = "100",
          amount = 10L
        )
      )
    }.apply {
      assertEquals(HttpStatusCode.Created, status)
      assertEquals("{\"destination\":{\"id\":\"100\",\"balance\":20}}", bodyAsText())
    }
  }
  
  @Test
  @Order(5)
  fun `Get balance for existing account`() = testApplication {
    application { module() }
    
    client.get("/balance?account_id=100").apply {
      assertEquals(HttpStatusCode.OK, status)
      assertEquals("20", bodyAsText())
    }
  }
  
  @Test
  @Order(6)
  fun `Withdraw from non-existing account`() = testApplication {
    application { module() }
    
    val client = createClient {
      install(ContentNegotiation) {
        json()
      }
    }
    
    client.post("/event") {
      contentType(ContentType.Application.Json)
      setBody(
        EventModel(
          type = EventType.Withdraw,
          origin = "200",
          amount = 10L
        )
      )
    }.apply {
      assertEquals(HttpStatusCode.NotFound, status)
      assertEquals("0", bodyAsText())
    }
  }
}