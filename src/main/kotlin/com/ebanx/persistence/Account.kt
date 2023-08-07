package com.ebanx.persistence

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*

class Account(id : EntityID<String>) : Entity<String>(id) {
  object Table : IdTable<String>("account") {
    override val id = varchar("id", 32).entityId()
    override val primaryKey = PrimaryKey(id)
    
    val balance = long("balance")
  }
  
  companion object : EntityClass<String, Account>(Table)
  
  var balance by Table.balance
  
  @Serializable
  data class Model(val id: String, val balance: Long)
  
  fun toModel() =
    Model(id.value, balance)
}