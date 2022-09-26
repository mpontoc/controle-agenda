package io.github.controleagenda.repository

import io.github.controleagenda.model.SubSegment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface SubSegmentRepository : JpaRepository<SubSegment, Long> {

    override fun findAll(): MutableList<SubSegment>

//    fun findSubSegmentById(id: Long): SubSegment

    @Query("FROM SubSegment WHERE segment_id = :idSegment")
    fun findSubSegmentFromSegmentID(@Param("idSegment") idSegment: Long): MutableList<SubSegment>

}