val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposed_version: String by project
val h2_version: String by project
val junit_version: String by project

plugins {
  kotlin("jvm") version "1.9.0"
  id("io.ktor.plugin") version "2.3.3"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

group = "com.ebanx"
version = "0.0.1"

application {
  mainClass.set("com.ebanx.ApplicationKt")
  
  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.ktor:ktor-server-core-jvm")
  implementation("io.ktor:ktor-server-compression-jvm")
  implementation("io.ktor:ktor-server-default-headers-jvm")
  implementation("io.ktor:ktor-server-forwarded-header-jvm")
  implementation("io.ktor:ktor-server-call-logging-jvm")
  implementation("io.ktor:ktor-server-call-id-jvm")
  implementation("io.ktor:ktor-server-content-negotiation-jvm")
  implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
  implementation("io.ktor:ktor-server-status-pages")
  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
  implementation("com.h2database:h2:$h2_version")
  implementation("io.ktor:ktor-server-netty-jvm")
  implementation("ch.qos.logback:logback-classic:$logback_version")
  testImplementation("io.ktor:ktor-server-tests-jvm")
  testImplementation("io.ktor:ktor-client-content-negotiation-jvm")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}
