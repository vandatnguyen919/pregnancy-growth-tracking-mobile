package com.pregnancy.domain.usecase.pregnancy

import com.pregnancy.domain.model.pregnancy.Pregnancy
import com.pregnancy.domain.repository.PregnancyRepository
import javax.inject.Inject

class GetOnGoingPregnancyUseCase @Inject constructor(
    private val pregnancyRepository: PregnancyRepository
) {
    suspend operator fun invoke(): Result<Pregnancy> {
        return pregnancyRepository.getMyOnGoingPregnancy()
    }
}