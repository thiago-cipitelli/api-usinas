package com.boltenergy.api_usinas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class ApiUsinasApplication {

    @GetMapping
    fun helloWorld() = "Hello"
}

fun main(args: Array<String>) {
	runApplication<ApiUsinasApplication>(*args)
}
