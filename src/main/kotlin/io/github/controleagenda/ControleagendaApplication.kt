package io.github.controleagenda

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class ControleAgendaApplication
fun main(args: Array < String > ) {
    SpringApplication.run(ControleAgendaApplication::class.java, * args)
}
