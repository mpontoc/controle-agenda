package io.github.controleagenda.services.impl

import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.exception.NotFoundException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.model.dto.SegmentDTO
import io.github.controleagenda.model.dto.SegmentResponse
import io.github.controleagenda.model.dto.SubSegmentDTO
import io.github.controleagenda.model.dto.UserDTO
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.repository.UserRepository
import io.github.controleagenda.services.SegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Suppress("UNCHECKED_CAST")
@Service
class SegmentServiceImpl : SegmentService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    val util = Util()

    override fun getSegmentById(userId: Long): SegmentToReturn {
        val user = userRepository.findUserById(userId)
        val allSegments: MutableList<Segment> = segmentRepository.findSegmentsFromUserID(userId)
        val allSubSegments: MutableList<SubSegment> = segmentRepository.findSubSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = mutableListOf()
        val subSegments: MutableList<SubSegmentDTO> = mutableListOf()

        return util.segmentToReturn(allSubSegments, subSegments, allSegments, segments, user)
    }

    override fun createSegment(userId: Long, segment: Segment): SegmentToReturn {

        val user = userRepository.findUserById(userId)
        val userToSet = userRepository.getById(userId)
        var allSegments = segmentRepository.findSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = mutableListOf()
        val subSegments: MutableList<SubSegmentDTO> = mutableListOf()

        if (allSegments.count() < 10) {

            val segmentToFill: Segment =
                segmentRepository.save(
                    Segment(
                        util.idSequenceSegment(segmentRepository),
                        segment.segmentName
                    )
                )

            util.createSubSegmentDefault(
                userToSet,
                userRepository,
                segmentToFill,
                segmentRepository,
                subSegmentRepository
            )

            allSegments = segmentRepository.findSegmentsFromUserID(userId)
            val allSubSegments = segmentRepository.findSubSegmentsFromUserID(userId)
            return util.segmentToReturn(allSubSegments, subSegments, allSegments, segments, user)
        } else throw BackendException("Atingiu a quantidade máxima Segmentos -> qtd max = 10")
    }

    private fun segmentToReturn(
        allSubSegments: MutableList<SubSegment>,
        subSegments: MutableList<SubSegmentDTO>,
        allSegments: MutableList<Segment>,
        segments: MutableList<SegmentDTO>,
        user: UserDTO
    ): SegmentToReturn {
        for (subSegment in allSubSegments) {
            subSegments.add(
                SubSegmentDTO(
                    subSegment.subSementId,
                    subSegment.subSegmentName,
                    subSegment.message,
                    SegmentResponse(
                        subSegment.segment!!.segmentId,
                        subSegment.segment.segmentName,
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
            user,
            segments
        )
    }

    override fun updateSegment(userId: Long, segment: Segment): SegmentToReturn {

        val user = userRepository.findUserById(userId)
        val allSegments = segmentRepository.findSegmentsFromUserID(userId)
        val allSubSegments = segmentRepository.findSubSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = ArrayList()
        val subSegments: MutableList<SubSegmentDTO> = ArrayList()

        if (segmentRepository.findById(segment.segmentId).isPresent) {
            segmentRepository.save(Segment(segment.segmentId, segment.segmentName))

            for (subSegment in allSubSegments) {
                subSegments.add(
                    SubSegmentDTO(
                        subSegment.subSementId,
                        subSegment.subSegmentName,
                        subSegment.message,
                        SegmentResponse(
                            subSegment.segment!!.segmentId,
                            subSegment.segment.segmentName
                        )
                    )
                )
            }

            for (segment in allSegments) {
                segments.add(
                    SegmentDTO(
                        segment.segmentId, segment.segmentName,
                        subSegments.filter {
                            it.segment.segmentId == segment.segmentId
                        } as MutableList<SubSegmentDTO?>
                    )
                )
            }

            return SegmentToReturn(
                user,
                segments
            )
        } else throw NotFoundException("Segmento com o id ${segment.segmentId} não existe no banco de dados")
    }

    override fun deleteSegment(id: Long): String {

        if (id in 1..6) {
            throw BackendException("Não é possível apagar os valores default")
        } else if (segmentRepository.findById(id).isPresent) {
            val subSegmentBySegmentId = subSegmentRepository.findSubSegmentFromSegmentID(id)
            subSegmentBySegmentId.forEach {
                it.subSementId
                subSegmentRepository.delete(it)
            }
            val segment = segmentRepository.findById(id)
            segmentRepository.deleteById(id)
            return ("O usuario ${segment.get().segmentName} foi deletado com sucesso")
        } else throw NotFoundException("Segmento com o id $id não existe no banco de dados")
    }

}

