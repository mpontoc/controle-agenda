package io.github.controleagenda.services.impl

import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.exception.NotFoundException
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
import io.github.controleagenda.services.SubSegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Suppress("UNCHECKED_CAST")
@Service
class SubSegmentServiceImpl : SubSegmentService {

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val util = Util()

    override fun createSubSegment(userId: Long, subSegment: SubSegment): SegmentToReturn {

        val user = userRepository.getById(userId)
        val allSegments: MutableList<Segment> = segmentRepository.findSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = mutableListOf()
        val subSegments: MutableList<SubSegmentDTO> = mutableListOf()
        val segmentToSet: Segment

        try {
            segmentToSet = segmentRepository.findSegmentFromUserID(userId, subSegment.segment!!.segmentId)
        } catch (e: Exception) {
            throw NotFoundException("Segmento com o id ${subSegment.segment!!.segmentId} não existe no banco de dados")
        }
        if (allSegments.count() < 20) {
            subSegmentRepository.save(
                SubSegment(
                    util.idSequenceSubSegment(subSegmentRepository),
                    subSegment.subSegmentName,
                    subSegment.message,
                    User(user.id, user.userName, user.password),
                    Segment(
                        segmentToSet.segmentId, segmentToSet.segmentName, user, subSegment
                    )
                )
            )
            return util.segmentToReturn(segmentRepository, userId, subSegments, subSegment.segment, allSegments, segments, user)
        } else throw BackendException("Excedeu a quantidade de 20 Tarefas por Segmento")
    }

    private fun segmentToReturn(
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

    override fun updateSubSegment(subSegment: SubSegment): SubSegment {
        if (subSegmentRepository.findById(subSegment.subSementId).isPresent) {
//            val subSegmentBase = subSegmentRepository.findSubSegmentById(subSegment.subSementId)
            return subSegmentRepository.save(
                SubSegment(
                    subSegment.subSementId, subSegment.subSegmentName, subSegment.message,
//                    Segment(subSegmentBase.segment.id, subSegmentBase.subSegmentName)
                )
            )
        } else throw NotFoundException("SubSegmento com o id ${subSegment.subSementId} não existe no banco de dados")
    }

    override fun deleteSubSegment(idSubSegment: Long) {
        if (subSegmentRepository.findById(idSubSegment).isPresent) {
            subSegmentRepository.deleteById(idSubSegment)
        } else throw NotFoundException("SubSegmento com o id ${idSubSegment} não existe no banco de dados")

    }
}