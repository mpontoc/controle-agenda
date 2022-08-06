package io.github.controleagenda.repository

import io.github.controleagenda.model.Segment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface SegmentRepository : JpaRepository<Segment?, Long?> {

    override fun getById(id: Long): Segment
    override fun findAll(): MutableList<Segment>

}


