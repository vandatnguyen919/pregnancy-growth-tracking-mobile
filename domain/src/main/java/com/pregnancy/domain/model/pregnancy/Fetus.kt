package com.pregnancy.domain.model.pregnancy

data class Fetus(
    val id: Long,
    val userId: Long,
    val pregnancyId: Long,
    val nickName: String,
    val gender: String
)