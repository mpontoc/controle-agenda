package io.github.controleagenda.services.impl

import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.exception.NotFoundException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.model.Users
import io.github.controleagenda.model.dto.SegmentDTO
import io.github.controleagenda.model.dto.SegmentResponse
import io.github.controleagenda.model.dto.SubSegmentDTO
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.repository.UsersRepository
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
    lateinit var usersRepository: UsersRepository

    val util = Util()

    override fun createSubSegment(usersId: Long, segmentId: Long, subSegment: SubSegment): SegmentToReturn {

        val users = usersRepository.findUsersById(usersId)
        val usersToSet = usersRepository.getById(usersId)
        val allSegments: MutableList<Segment> = segmentRepository.findSegmentsFromusersID(usersId)
        val segments: MutableList<SegmentDTO> = mutableListOf()
        val subSegments: MutableList<SubSegmentDTO> = mutableListOf()

        val segmentToSet: Segment

        try {
            segmentToSet = segmentRepository.findSegmentFromusersID(usersId, segmentId)
        } catch (e: Exception) {
            throw NotFoundException("Segmento com o id ${subSegment.segment!!.id} não existe no banco de dados")
        }
        if (allSegments.count() < 20) {
            subSegmentRepository.save(
                SubSegment(
                    subSegment.id,
                    subSegment.subSegmentName,
                    subSegment.message,
                    Users(usersToSet.id),
                    Segment(
                        segmentToSet.id, segmentToSet.segmentName, usersToSet,
                        mutableListOf(subSegment)
                    )
                )
            )
            val allSubSegments: MutableList<SubSegment> = segmentRepository.findSubSegmentsFromusersID(usersId)
            return util.segmentToReturn(allSubSegments, subSegments, allSegments, segments, users)
        } else throw BackendException("Excedeu a quantidade de 20 Tarefas por Segmento")
    }


    override fun updateSubSegment(usersId: Long, subSegment: SubSegment): SubSegmentDTO {
        val subSegmentToEdit: SubSegment
        val usersToSet = usersRepository.getById(usersId)
        try {
            subSegmentToEdit = subSegmentRepository.findSubSegmentFromusersId(usersId, subSegment.id)
        } catch (e: Exception) {
            throw NotFoundException("SubSegmento com o id ${subSegment.id} não existe no banco de dados")
        }
        if (subSegmentToEdit != null) {
            val subSegmentToReturn = subSegmentRepository.save(
                SubSegment(
                    subSegment.id, subSegment.subSegmentName, subSegment.message,
                    usersToSet,
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

    override fun deleteSubSegment(usersId: Long, segmentId: Long, subSegmentId: Long) {
        val subSegmentToDelete: SubSegment
        try {
            subSegmentToDelete =
                subSegmentRepository.findSubSegmentFromusersIdAndSegmentId(usersId, segmentId, subSegmentId)
        } catch (e: Exception) {
            throw NotFoundException("SubSegmento com o id $subSegmentId não existe no banco de dados")
        }
        if (subSegmentToDelete != null) {
            subSegmentRepository.deleteById(subSegmentId)
        } else throw NotFoundException("SubSegmento com o id $subSegmentId não existe no banco de dados")
    }
}