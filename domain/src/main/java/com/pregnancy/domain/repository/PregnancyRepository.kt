package com.pregnancy.domain.repository

import com.pregnancy.domain.model.pregnancy.Fetus
import com.pregnancy.domain.model.pregnancy.Pregnancy

interface PregnancyRepository {

    suspend fun getMyOnGoingPregnancy(): Result<Pregnancy>

    suspend fun getGestationalWeekInsight(): Result<String>

    suspend fun getMyFetuses(): Result<List<Fetus>>
}