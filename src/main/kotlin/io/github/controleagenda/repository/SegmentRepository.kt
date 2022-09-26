package io.github.controleagenda.repository

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface SegmentRepository : JpaRepository<Segment, Long> {

    //    fun findSegmentById(segmentId: Long): Segment
    override fun findAll(): MutableList<Segment>

    @Query("FROM Segment WHERE user_id = :idUser")
    fun findSegmentsFromUserID(@Param("idUser") idUser: Long): MutableList<Segment>

    @Query("FROM Segment WHERE user_id = :idUser AND segment_id = :segmentId")
    fun findSegmentFromUserID(@Param("idUser") idUser: Long, @Param("segmentId") segmentId: Long): Segment

    @Query("FROM SubSegment WHERE user_id = :idUser")
    fun findSubSegmentsFromUserID(@Param("idUser") idUser: Long): MutableList<SubSegment>

}


