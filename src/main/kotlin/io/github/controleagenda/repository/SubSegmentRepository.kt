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

    @Query("FROM SubSegment WHERE user_id = :userId")
    fun findSubSegmentsFromUserId(@Param("userId") userId: Long): MutableList<SubSegment>

    @Query("FROM SubSegment WHERE id = :subSegmentId AND user_id = :userId")
    fun findSubSegmentFromUserId(@Param("userId") userId: Long, @Param("subSegmentId") subSegmentId: Long): SubSegment

    @Query("FROM SubSegment WHERE id = :subSegmentId AND user_id = :userId AND segment_id = :segmentId")
    fun findSubSegmentFromUserIdAndSegmentId(
        @Param("userId") userId: Long,
        @Param("segmentId") segmentId: Long,
        @Param("subSegmentId") subSegmentId: Long
    ): SubSegment


}