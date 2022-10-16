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

    @Query("FROM Segment WHERE user_id = :userId")
    fun findSegmentsFromUserID(@Param("userId") idUser: Long): MutableList<Segment>

    @Query("FROM Segment WHERE user_id = :userId AND id = :segmentId")
    fun findSegmentFromUserID(@Param("userId") idUser: Long, @Param("segmentId") segmentId: Long): Segment

    @Query("FROM SubSegment WHERE user_id = :userId")
    fun findSubSegmentsFromUserID(@Param("userId") idUser: Long): MutableList<SubSegment>

    @Query("FROM SubSegment WHERE segment_id = :segmentId")
    fun findSubSegmentsFromSegmentID(@Param("segmentId") segmentId: Long): MutableList<SubSegment>



}


