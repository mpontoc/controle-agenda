package io.github.controleagenda.controller

import io.github.controleagenda.model.Segments
import io.github.controleagenda.services.SegmentsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/segmentos")
class SegmentoController {

    @Autowired
    lateinit var segmentsService: SegmentsService

    @GetMapping()
    fun getAllUsers() =
        segmentsService.getSegments()

    @GetMapping("/{id}")
    fun getSegmentoById(@PathVariable id: Long): Optional<Segments?> {

        return segmentsService.getSegmentById(id)
    }


//    @PostMapping()
//
////    fun createSegments(@RequestBody user: User): User? {
////
////
////
////        val userRetorned : User? = userRepository?.findByName(name = user.login)
////
////        return userRetorned
////    }
}