package io.github.controleagenda.util

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.model.User
import io.github.controleagenda.model.dto.SegmentDTO
import io.github.controleagenda.model.dto.SegmentResponse
import io.github.controleagenda.model.dto.SubSegmentDTO
import io.github.controleagenda.model.dto.UserDTO
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.repository.UserRepository

open class Util {

    val segments: List<String> = listOf(
        "Academia", "Alimentação", "Educação", "Esporte", "Familiar", "Saúde"
    )

    fun subSegmentDefault(subSegmentRepository: SubSegmentRepository) =
        SubSegment(idSequenceSubSegment(subSegmentRepository), "crie a sua tarefa", "aqui descreva sua tarefa")

    fun initSegments(
        userID: Long,
        userRepository: UserRepository,
        segmentRepository: SegmentRepository,
        subSegmentRepository: SubSegmentRepository
    ) {

        val user: User = userRepository.getById(userID)
        val subSegmentDefaultToFill = subSegmentDefault(subSegmentRepository)

        for (segment in segments) {
            val segmentToFill: Segment = segmentRepository.save(Segment(idSequenceSegment(segmentRepository), segment))
            val subSegmentToFill: SubSegment = subSegmentRepository.save(
                SubSegment(
                    idSequenceSubSegment(subSegmentRepository),
                    subSegmentDefaultToFill.subSegmentName,
                    subSegmentDefaultToFill.message,
                    user,
                    segmentToFill
                )
            )
            userRepository.save(
                User(
                    user.id, user.userName, user.password,
                    mutableListOf(
                        segmentRepository.save(
                            Segment(
                                segmentToFill.id, segmentToFill.segmentName,
                                user,
                                mutableListOf(subSegmentToFill)
                            )
                        )
                    ),
                )
            )
        }
    }

    fun createSubSegmentDefault(
        user: User,
        userRepository: UserRepository,
        segment: Segment,
        segmentRepository: SegmentRepository,
        subSegmentRepository: SubSegmentRepository
    ): SubSegment {

        val subSegmentDefaultToFill = subSegmentDefault(subSegmentRepository)

        val subSegmentToFill: SubSegment = subSegmentRepository.save(
            SubSegment(
                subSegmentDefaultToFill.id,
                subSegmentDefaultToFill.subSegmentName,
                subSegmentDefaultToFill.message,
                user,
                segment
            )
        )
        userRepository.save(
            User(
                user.id, user.userName, user.password,
                mutableListOf(
                    segmentRepository.save(
                        Segment(
                            segment.id, segment.segmentName,
                            user,
                            mutableListOf(subSegmentToFill)
                        )
                    ),
                )
            )
        )

        return subSegmentToFill
    }

    fun idSequenceUser(userRepository: UserRepository): Long {

        var idSequenceOK = false
        var idSequence = 1

        while (!idSequenceOK) {
            if (userRepository.findById(idSequence.toLong()).isPresent) {
                idSequence++
            } else {
                idSequenceOK = true
            }
        }
        return idSequence.toLong()
    }

    fun idSequenceSegment(segmentRepository: SegmentRepository): Long {

        var idSequenceOK = false
        var idSequence = 1

        while (!idSequenceOK) {
            if (segmentRepository.findById(idSequence.toLong()).isPresent) {
                idSequence++
            } else {
                idSequenceOK = true
            }
        }
        return idSequence.toLong()
    }

    fun idSequenceSubSegment(subSegmentRepository: SubSegmentRepository): Long {

        var idSequenceOK = false
        var idSequence = 1

        while (!idSequenceOK) {
            if (subSegmentRepository.findById(idSequence.toLong()).isPresent) {
                idSequence++
            } else {
                idSequenceOK = true
            }
        }
        return idSequence.toLong()
    }

    fun segmentToReturn(
        allSubSegments: MutableList<SubSegment>,
        subSegments: MutableList<SubSegmentDTO>,
        allSegments: MutableList<Segment>,
        segments: MutableList<SegmentDTO>,
        user: UserDTO
    ): SegmentToReturn {
        for (subSegment in allSubSegments) {
            subSegments.add(
                SubSegmentDTO(
                    subSegment.id,
                    subSegment.subSegmentName,
                    subSegment.message,
                    SegmentResponse(
                        subSegment.segment!!.id,
                        subSegment.segment.segmentName,
                    )
                )
            )
        }

        for (segment in allSegments) {

            val subSegmentToReturn: MutableList<SubSegmentDTO> = mutableListOf()
            for (subSegment in subSegments) {
                if (subSegment.segment.segmentId == segment.id) {
                    subSegmentToReturn.add(
                        SubSegmentDTO(
                            subSegment.subSegmentId,
                            subSegment.subSegmentName,
                            subSegment.message,
                            SegmentResponse(segment.id, segment.segmentName)
                        )
                    )
                }
            }

            segments.add(
                SegmentDTO(
                    segment.id, segment.segmentName,
                    subSegmentToReturn.toMutableList()
                )
            )
        }
        return SegmentToReturn(
            user,
            segments
        )
    }
}