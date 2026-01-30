package com.boltenergy.api_usinas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableScheduling
@EntityScan("com.boltenergy.api_usinas.models")
@SpringBootApplication
class ApiUsinasApplication {

}

fun main(args: Array<String>) {
	runApplication<ApiUsinasApplication>(*args)
}
