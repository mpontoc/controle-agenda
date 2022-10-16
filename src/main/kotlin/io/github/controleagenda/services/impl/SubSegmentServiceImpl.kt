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
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.repository.UserRepository
import io.github.controleagenda.services.SubSegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

//@Suppress("UNCHECKED_CAST")
@Service
class SubSegmentServiceImpl : SubSegmentService {

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val util = Util()

    override fun createSubSegment(userId: Long, segmentId: Long, subSegment: SubSegment): SegmentToReturn {

        val user = userRepository.findUserById(userId)
        val userToSet = userRepository.getById(userId)
        val allSegments: MutableList<Segment> = segmentRepository.findSegmentsFromUserID(userId)
        val segments: MutableList<SegmentDTO> = mutableListOf()
        val subSegments: MutableList<SubSegmentDTO> = mutableListOf()

        val segmentToSet: Segment

        try {
            segmentToSet = segmentRepository.findSegmentFromUserID(userId, segmentId)
        } catch (e: Exception) {
            throw NotFoundException("Segmento com o id ${subSegment.segment!!.id} não existe no banco de dados")
        }
        if (allSegments.count() < 20) {
            subSegmentRepository.save(
                SubSegment(
                    subSegment.id,
                    subSegment.subSegmentName,
                    subSegment.message,
                    User(userToSet.id),
                    Segment(
                        segmentToSet.id, segmentToSet.segmentName, userToSet,
                        mutableListOf(subSegment)
                    )
                )
            )
            val allSubSegments: MutableList<SubSegment> = segmentRepository.findSubSegmentsFromUserID(userId)
            return util.segmentToReturn(allSubSegments, subSegments, allSegments, segments, user)
        } else throw BackendException("Excedeu a quantidade de 20 Tarefas por Segmento")
    }


    override fun updateSubSegment(userId: Long, subSegment: SubSegment): SubSegmentDTO {
        val subSegmentToEdit: SubSegment
        val userToSet = userRepository.getById(userId)
        try {
            subSegmentToEdit = subSegmentRepository.findSubSegmentFromUserId(userId, subSegment.id)
        } catch (e: Exception) {
            throw NotFoundException("SubSegmento com o id ${subSegment.id} não existe no banco de dados")
        }
        if (subSegmentToEdit != null) {
            val subSegmentToReturn = subSegmentRepository.save(
                SubSegment(
                    subSegment.id, subSegment.subSegmentName, subSegment.message,
                    userToSet,
                    Segment(subSegmentToEdit.segment!!.id, subSegmentToEdit.segment.segmentName)
                )
            )
            return SubSegmentDTO(
                subSegmentToReturn.id,
                subSegmentToReturn.subSegmentName,
                subSegmentToReturn.message,
                SegmentResponse(subSegmentToReturn.segment!!.id, subSegmentToReturn.segment.segmentName)
            )
        } else throw NotFoundException("SubSegmento com o id ${subSegment.id} não existe no banco de dados")
    }

    override fun deleteSubSegment(userId: Long, segmentId: Long, subSegmentId: Long) {
        val subSegmentToDelete: SubSegment
        try {
            subSegmentToDelete =
                subSegmentRepository.findSubSegmentFromUserIdAndSegmentId(userId, segmentId, subSegmentId)
        } catch (e: Exception) {
            throw NotFoundException("SubSegmento com o id $subSegmentId não existe no banco de dados")
        }
        if (subSegmentToDelete != null) {
            subSegmentRepository.deleteById(subSegmentId)
        } else throw NotFoundException("SubSegmento com o id $subSegmentId não existe no banco de dados")
    }
}