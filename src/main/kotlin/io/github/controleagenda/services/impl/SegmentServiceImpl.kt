package io.github.controleagenda.services.impl

import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.exception.NotFoundException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Suppress("UNCHECKED_CAST")
@Service
class SegmentServiceImpl : SegmentService {

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    val util = Util()

    override fun getSegmentById(id: Long): SegmentToReturn {

        if (segmentRepository.findById(id).isPresent) {
            val segment = segmentRepository.findSegmentById(id)
            return SegmentToReturn(
                Segment(segment.id, segment.segmentName),
                subSegmentRepository.findSubSegmentToSegmentID(id)
            )
        } else throw NotFoundException("Segmento com o id $id não existe no banco de dados")
    }

    override fun getAllSegments(): MutableList<SegmentToReturn> {

        val allSegments = segmentRepository.findAll()
        var subSegmentToSegment: MutableList<SubSegment>

        if (allSegments.count() <= 1) {
            util.initSegments(segmentRepository, subSegmentRepository)
        }

        val allSegmentList: MutableList<Segment> = allSegments
        val segmentToReturn = mutableListOf<SegmentToReturn>()
        val subSegments = subSegmentRepository.findAll()

        for (segment in allSegmentList) {
            subSegmentToSegment =
                subSegments.filter {
                    it!!.segment.id == (segment.id)
                } as MutableList<SubSegment>
            segmentToReturn.add(
                SegmentToReturn(
                    Segment(segment.id, segment.segmentName.toString()),
                    subSegmentToSegment.toMutableList()
                )
            )
            subSegmentToSegment.clear()
        }
        return segmentToReturn
    }

    override fun createSegment(segment: Segment): SegmentToReturn {

        val allSegments = segmentRepository.findAll().count()
        val idSequence: Long

        if (allSegments < 10) {
            if (segment.id != null && allSegments < segment.id && !segmentRepository.findById(segment.id).isPresent) {
                idSequence = segment.id
            } else {
                idSequence = util.idSequenceSegment(segmentRepository)
            }
            return SegmentToReturn(
                segmentRepository.save(
                    Segment(idSequence, segment.segmentName)
                ),
                mutableListOf(
                    subSegmentRepository.save(
                        SubSegment(
                            util.idSequenceSubSegment(subSegmentRepository),
                            util.subSegmentDefault.subSegmentName,
                            util.subSegmentDefault.message,
                            Segment(idSequence, segment.segmentName)
                        )
                    )
                )
            )
        } else throw BackendException("Atingiu a quantidade máxima Segmentos -> qtd max = 10")
    }

    override fun updateSegment(segment: Segment): SegmentToReturn {

        if (segmentRepository.findById(segment.id!!).isPresent) {
            val segmentToEdit = segmentRepository.save(Segment(segment.id, segment.segmentName))

            return SegmentToReturn(
                segmentToEdit,
                subSegmentRepository.findSubSegmentToSegmentID(segment.id)
            )
        } else throw NotFoundException("Segmento com o id ${segment.id} não existe no banco de dados")
    }

    override fun deleteSegment(id: Long): String {

        if (id in 1..6) {
            throw BackendException("Não é possível apagar os valores default")
        } else if (segmentRepository.findById(id).isPresent) {
            val subSegmentBySegmentId = subSegmentRepository.findSubSegmentToSegmentID(id)
            subSegmentBySegmentId.forEach {
                it!!.id
                subSegmentRepository.delete(it)
            }
            val segment = segmentRepository.findById(id)
            segmentRepository.deleteById(id)
            return ("O usuario ${segment.get().segmentName} foi deletado com sucesso")
        } else throw NotFoundException("Segmento com o id $id não existe no banco de dados")
    }

}

