package io.github.controleagenda.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Hello {

    @CrossOrigin
    @GetMapping("/hello")
    fun test(): String {
        return "hello world"
    }
}