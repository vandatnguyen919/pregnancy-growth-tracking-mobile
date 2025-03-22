package com.pregnancy.domain.usecase.pregnancy

import com.pregnancy.domain.repository.PregnancyRepository
import javax.inject.Inject

class GetGestationalWeekInsightUseCase @Inject constructor(
    private val pregnancyRepository: PregnancyRepository
) {
    suspend operator fun invoke(): Result<String> {
        return pregnancyRepository.getGestationalWeekInsight()
    }
}