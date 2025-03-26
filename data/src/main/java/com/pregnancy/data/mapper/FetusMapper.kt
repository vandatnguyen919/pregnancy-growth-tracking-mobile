package com.pregnancy.data.mapper

import com.pregnancy.data.source.remote.model.pregnancy.FetusDto
import com.pregnancy.domain.model.pregnancy.Fetus

fun FetusDto.toDomain(): Fetus {
    return Fetus(
        id = id,
        userId = userId,
        pregnancyId = pregnancyId,
        nickName = nickName,
        gender = gender
    )
}