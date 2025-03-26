package com.pregnancy.data.source.remote.model.pregnancy

data class FetusDto(
    val id: Long,
    val userId: Long,
    val pregnancyId: Long,
    val nickName: String,
    val gender: String
)