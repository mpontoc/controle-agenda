package io.github.controleagenda.repository

import io.github.controleagenda.model.Segments
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SegmentsRepository : JpaRepository<Segments?, Long?>