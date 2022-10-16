package io.github.controleagenda.model

import java.time.LocalDateTime

data class BackendError(

    val timestamp: LocalDateTime? = LocalDateTime.now(),
    val status: Int?,
    val error: String?,
    val message: String?,
    val path: String?

)
