package io.github.controleagenda.services.impl

import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.exception.NotFoundException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentOnlyOneToReturn
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.model.dto.SegmentDTO
import io.github.controleagenda.model.dto.SegmentResponse
import io.github.controleagenda.model.dto.SubSegmentDTO
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

    override fun getAllSegments(userId: Long): SegmentToReturn {
        val user = userRepository.findUserById(userId)
        val allSegments: MutableList<Segment> = segmentRepository.findSegmentsFromUserID(userId)
        val allSubSegments: MutableList<SubSegment> = segmentRepository.findSubSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = mutableListOf()
        val subSegments: MutableList<SubSegmentDTO> = mutableListOf()

        return util.segmentToReturn(allSubSegments, subSegments, allSegments, segments, user)
    }

    override fun getSegmentById(userId: Long, segmentId: Long): SegmentOnlyOneToReturn {

        val segment = segmentRepository.findSegmentFromUserID(userId, segmentId)
        val user = userRepository.findUserById(userId)
        val allSubSegments: MutableList<SubSegment> = segmentRepository.findSubSegmentsFromSegmentID(segmentId)

        val subSegmentToReturn: MutableList<SubSegmentDTO> = mutableListOf()
        for (subSegment in allSubSegments) {
            if (subSegment.segment!!.id == segment.id) {
                subSegmentToReturn.add(
                    SubSegmentDTO(
                        subSegment.id,
                        subSegment.subSegmentName,
                        subSegment.message,
                        SegmentResponse(segment.id, segment.segmentName)
                    )
                )
            }
        }

        return SegmentOnlyOneToReturn(
            user,
            SegmentDTO(
                segment.id, segment.segmentName,
                subSegmentToReturn.toMutableList()
            )
        )
    }

    override fun createSegment(userId: Long, segment: Segment): SegmentToReturn {

        val user = userRepository.findUserById(userId)
        val userToSet = userRepository.getById(userId)
        var allSegments = segmentRepository.findSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = mutableListOf()
        val subSegments: MutableList<SubSegmentDTO?> = mutableListOf()

        if (allSegments.count() < 10) {
            val segmentToFill: Segment =
                segmentRepository.save(
                    Segment(
                        segment.id,
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


            val allSubSegments: MutableList<SubSegment> =
                segmentRepository.findSubSegmentsFromSegmentID(segmentToFill.id)

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
//            return util.segmentToReturn(allSubSegments, subSegments, allSegments, segments, user)
            return SegmentToReturn(
                user,
                mutableListOf(SegmentDTO(segmentToFill.id, segmentToFill.segmentName, subSegments)),
            )
        } else throw BackendException("Atingiu a quantidade máxima Segmentos -> qtd max = 10")
    }

    override fun updateSegment(userId: Long, segment: Segment): SegmentToReturn {

        val user = userRepository.findUserById(userId)
        val allSubSegments = segmentRepository.findSubSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = ArrayList()
        val subSegments: MutableList<SubSegmentDTO> = ArrayList()

        val segmentToUpdate: Segment

        try {
            segmentToUpdate = segmentRepository.findSegmentFromUserID(userId, segment.id)
        } catch (e: Exception) {
            throw NotFoundException("Segmento com o id ${segment.id} não existe no banco de dados")
        }

        if (segmentToUpdate != null) {
            segmentRepository.save(
                Segment(
                    segment.id,
                    segment.segmentName,
                    segmentToUpdate.user,
                    segmentToUpdate.subSegment
                )
            )
            val allSegments = segmentRepository.findSegmentsFromUserID(userId)

            return util.segmentToReturn(allSubSegments, subSegments, allSegments, segments, user)
        } else throw NotFoundException("Segmento com o id ${segment.id} não existe no banco de dados")
    }

    override fun deleteSegment(userId: Long, segmentId: Long): String {

        val segmentToDelete = segmentRepository.findSegmentFromUserID(userId, segmentId)

        segmentToDelete.let {
            for (segment in util.segments)
                if (segment == segmentToDelete.segmentName)
                    throw BackendException("Não é possível apagar os valores default")
        }

        if (segmentToDelete != null) {
            val subSegmentsToDelete = segmentRepository.findSubSegmentsFromSegmentID(segmentId)

            subSegmentsToDelete.forEach {
                subSegmentRepository.deleteById(it.id)
//                subSegmentRepository.delete(subseg)
            }

            segmentRepository.deleteById(segmentToDelete.id)
            return ("O usuario ${segmentToDelete.segmentName} foi deletado com sucesso")
        } else throw NotFoundException("Segmento com o id $segmentId não existe no banco de dados")
    }

}

