package io.github.controleagenda.repository

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface SubSegmentRepository : JpaRepository<SubSegment?, Long?> {

    override fun findAll(): MutableList<SubSegment?>

}