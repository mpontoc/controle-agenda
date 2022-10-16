package io.github.controleagenda.repository

import io.github.controleagenda.model.SubSegment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface SubSegmentRepository : JpaRepository<SubSegment, Long> {

    override fun findAll(): MutableList<SubSegment>

    fun findSubSegmentById(id: Long): SubSegment

    @Query("FROM SubSegment WHERE users_id = :usersId")
    fun findSubSegmentsFromusersId(@Param("usersId") usersId: Long): MutableList<SubSegment>

    @Query("FROM SubSegment WHERE id = :subSegmentId AND users_id = :usersId")
    fun findSubSegmentFromusersId(@Param("usersId") usersId: Long, @Param("subSegmentId") subSegmentId: Long): SubSegment

    @Query("FROM SubSegment WHERE id = :subSegmentId AND users_id = :usersId AND segment_id = :segmentId")
    fun findSubSegmentFromusersIdAndSegmentId(
        @Param("usersId") usersId: Long,
        @Param("segmentId") segmentId: Long,
        @Param("subSegmentId") subSegmentId: Long
    ): SubSegment


}