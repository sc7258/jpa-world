package com.sc7258.jpaworld

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "World Database API",
        version = "1.0",
        description = "REST API for the MySQL World Database",
        contact = Contact(name = "Developer", email = "developer@example.com"),
        license = License(name = "MIT License", url = "https://opensource.org/licenses/MIT")
    ),
    servers = [
        Server(url = "/api/v1", description = "Default Server URL")
    ]
)
class JpaWorldApplication

fun main(args: Array<String>) {
    runApplication<JpaWorldApplication>(*args)
}
