package io.github.controleagenda.services.impl

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SegmentServiceImpl : SegmentService {

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    val util = Util()

    override fun getSegmentById(id: Long): SegmentToReturn {

        val segment = segmentRepository.findSegmentById(id)

        return SegmentToReturn(
            Segment(segment.id, segment.segment),
            subSegmentRepository.findSubSegmentToSegmentID(id)
        )
    }

    override fun getAllSegments(): MutableList<SegmentToReturn> {
        var allSegments = segmentRepository.findAll().count()
        var subSegmentToSegment: MutableList<SubSegment>

        if (allSegments <= 1) {
            util.initSegments(segmentRepository, subSegmentRepository)
        }

        val allSegmentList: MutableList<Segment> = segmentRepository.findAll()
        var segmentos = mutableListOf<SegmentToReturn>()
        val subSegments = subSegmentRepository.findAll()

        for (segment in allSegmentList) {
            subSegmentToSegment =
                subSegments.filter {
                    it!!.segment.id == (segment.id)
                } as MutableList<SubSegment>

            segmentos.add(
                SegmentToReturn(
                    Segment(segment.id, segment.segment.toString()),
                    subSegmentToSegment.toMutableList()
                )
            )
            subSegmentToSegment.clear()
        }

        return segmentos
    }

    override fun createSegment(id: Long, segment: Segment): SegmentToReturn {

        val allSegments = segmentRepository.findAll().count()
        var idSequenceOK = false
        var idSequence: Long = 5
        val idSubSeg = util.idSequentSubSegment(subSegmentRepository)

        if (allSegments < id && !segmentRepository.findById(id).isPresent) {
            return SegmentToReturn(
                this.segmentRepository.save(Segment(id, segment.segment)),
                mutableListOf(
                    subSegmentRepository.save(
                        SubSegment(
                            util.idSequentSubSegment(subSegmentRepository),
                            util.subSegmentDefault.subSegment,
                            util.subSegmentDefault.message,
                            Segment(id, segment.segment)
                        )
                    )
                )
            )
        } else {
            while (!idSequenceOK) {
                if (segmentRepository.findById(idSequence).isPresent) {
                    idSequence++
                } else {
                    idSequenceOK = true
                }
            }
        }

        return SegmentToReturn(
            segmentRepository.save(
                Segment(idSequence, segment.segment)
            ),
            mutableListOf(
                subSegmentRepository.save(
                    SubSegment(
                        util.idSequentSubSegment(subSegmentRepository),
                        util.subSegmentDefault.subSegment,
                        util.subSegmentDefault.message,
                    )
                )
            )
        )
    }

    override fun updateSegment(segment: Segment): SegmentToReturn {

        val segmentEdited = segmentRepository.save(Segment(segment.id, segment.segment))

        return SegmentToReturn(
            segmentEdited,
            subSegmentRepository.findSubSegmentToSegmentID(segment.id!!)
        )
    }

    override fun deleteSegment(id: Long) {

        if (id in 1..6) {
            throw RuntimeException("Não é possível apagar os valores default")
        } else {
            val subSegmentBySegmentId = subSegmentRepository.findSubSegmentToSegmentID(id)
            subSegmentBySegmentId.forEach {
                it!!.id
                subSegmentRepository.delete(it)
            }
            val segment = segmentRepository.findById(id)
            segmentRepository.deleteById(id)
            println("O usuario ${segment.get().segment} foi deletado com sucesso")
        }
    }

}

