package com.pregnancy.data.source.remote.model.pregnancy

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class PregnancyDto(
    val id: Long,
    val maternalAge: Int,
    @SerializedName("pregnancyStartDate")
    val startDate: LocalDate,
    @SerializedName("dueDate")
    val expectedDueDate: LocalDate,
    val deliveryDate: LocalDate,
)
