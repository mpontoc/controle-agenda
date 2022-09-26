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

    val subSegmentDefault: List<String> = listOf("crie a sua tarefa", "aqui descreva sua tarefa")

    fun initSegments(
        userID: Long,
        userRepository: UserRepository,
        segmentRepository: SegmentRepository,
        subSegmentRepository: SubSegmentRepository
    ) {

        val user: User = userRepository.getById(userID)

        val segments: List<String> = listOf(
            "Academia", "Alimentação", "Educação", "Esporte", "Familiar", "Saúde"
        )

        for (segment in segments) {
            val segmentToFill: Segment = segmentRepository.save(Segment(idSequenceSegment(segmentRepository), segment))
            val subSegmentToFill: SubSegment = subSegmentRepository.save(
                SubSegment(
                    idSequenceSubSegment(subSegmentRepository),
                    subSegmentDefault[0],
                    subSegmentDefault[1],
                    user,
                    segmentToFill
                )
            )
            userRepository.save(
                User(
                    user.id, user.userName, user.password,
                    segmentRepository.save(
                        Segment(
                            segmentToFill.segmentId, segmentToFill.segmentName,
                            user,
                            subSegmentToFill
                        )
                    ),
                    subSegmentToFill
                )
            )
        }
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
        segmentRepository: SegmentRepository,
        userId: Long,
        subSegments: MutableList<SubSegmentDTO>,
        segment: Segment,
        allSegments: MutableList<Segment>,
        segments: MutableList<SegmentDTO>,
        user: User
    ): SegmentToReturn {
        val allSubSegments: MutableList<SubSegment> = segmentRepository.findSubSegmentsFromUserID(userId)
        for (subSegment in allSubSegments) {
            subSegments.add(
                SubSegmentDTO(
                    subSegment.subSementId,
                    subSegment.subSegmentName,
                    subSegment.message,
                    SegmentResponse(
                        subSegment.segment!!.segmentId,
                        segment.segmentName,
                    )
                )
            )
        }
        for (segment in allSegments) {
            val subSegmentToReturn: MutableList<SubSegmentDTO> = mutableListOf()
            for (subSegment in subSegments) {
                if (subSegment.segment.segmentId == segment.segmentId) {
                    subSegmentToReturn.add(
                        SubSegmentDTO(
                            subSegment.subSegmentId,
                            subSegment.subSegmentName,
                            subSegment.message,
                            SegmentResponse(segment.segmentId, segment.segmentName)
                        )
                    )
                }
            }

            segments.add(
                SegmentDTO(
                    segment.segmentId, segment.segmentName,
                    subSegmentToReturn.toMutableList()
                )
            )
        }
        return SegmentToReturn(
            UserDTO(user.id!!, user.userName!!),
            segments
        )
    }
}