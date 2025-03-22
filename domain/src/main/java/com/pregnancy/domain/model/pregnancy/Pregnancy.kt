package com.pregnancy.domain.model.pregnancy

import java.time.LocalDate

data class Pregnancy(
    val id: Long,
    val maternalAge: Int,
    val startDate: LocalDate,
    val expectedDueDate: LocalDate,
    val deliveryDate: LocalDate,
)
