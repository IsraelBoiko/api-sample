package com.ebanx.service

import com.ebanx.core.*
import com.ebanx.persistence.*
import kotlinx.serialization.*

@Serializable
enum class EventType {
  @SerialName("deposit")
  Deposit,
  
  @SerialName("withdraw")
  Withdraw,
  
  @SerialName("transfer")
  Transfer,
}

@Serializable
data class EventModel(val type: EventType, val origin: String? = null, val destination: String? = null, val amount: Long)

class EventService {
  companion object : EventServiceTrait
}

interface EventServiceTrait {
  suspend fun register(model: EventModel) = run {
    when (model.type) {
      EventType.Deposit ->
        deposit(model.destination ?: throwBadRequest(), model.amount)
      
      EventType.Withdraw ->
        withdraw(model.origin ?: throwBadRequest(), model.amount)
      
      EventType.Transfer ->
        transfer(model.origin ?: throwBadRequest(), model.destination ?: throwBadRequest(), model.amount)
    }
  }
  
  private suspend fun deposit(destination: String, amount: Long) = transaction {
    mapOf(
      "destination" to Account.findById(destination).opt({
        it.balance += amount
        
        it
      }, {
        Account.new(destination) {
          balance = amount
        }
      }).toModel()
    )
  }
  
  private suspend fun withdraw(originId: String, amount: Long) = transaction {
    val origin = Account.findById(originId) ?: throwNotFound()
    
    origin.balance -= amount
    
    mapOf("origin" to origin.toModel())
  }
  
  private suspend fun transfer(originId: String, destinationId: String, amount: Long) = transaction {
    val origin = Account.findById(originId) ?: throwNotFound()
    val destination = Account.findById(destinationId) ?: Account.new(destinationId) { balance = 0L }
    
    origin.balance -= amount
    destination.balance += amount
    
    mapOf(
      "origin" to origin.toModel(),
      "destination" to destination.toModel(),
    )
  }
}