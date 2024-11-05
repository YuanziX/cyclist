package dev.yuanzix.cyclist.dash.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toCustomString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")
    return this.format(formatter)
}
