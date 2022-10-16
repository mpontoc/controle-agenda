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

    @Query("FROM Segment WHERE users_id = :usersId")
    fun findSegmentsFromusersID(@Param("usersId") idUsers: Long): MutableList<Segment>

    @Query("FROM Segment WHERE users_id = :usersId AND id = :segmentId")
    fun findSegmentFromusersID(@Param("usersId") idUsers: Long, @Param("segmentId") segmentId: Long): Segment

    @Query("FROM SubSegment WHERE users_id = :usersId")
    fun findSubSegmentsFromusersID(@Param("usersId") idUsers: Long): MutableList<SubSegment>

    @Query("FROM SubSegment WHERE segment_id = :segmentId")
    fun findSubSegmentsFromSegmentID(@Param("segmentId") segmentId: Long): MutableList<SubSegment>



}


