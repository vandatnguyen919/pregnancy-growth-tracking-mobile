package com.pregnancy.data.mapper

import com.pregnancy.data.source.remote.model.pregnancy.PregnancyDto
import com.pregnancy.domain.model.pregnancy.Pregnancy

fun PregnancyDto.toDomain() : Pregnancy {
    return Pregnancy(
        id = id,
        maternalAge = maternalAge,
        startDate = startDate,
        expectedDueDate = expectedDueDate,
        deliveryDate = deliveryDate
    )
}