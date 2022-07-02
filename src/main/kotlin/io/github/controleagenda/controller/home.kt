package io.github.controleagenda.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class home {

    @GetMapping
    fun test() : String {

        val a : String   = "hello world"

        return  a
    }
}