package com.pregnancy.domain.usecase.pregnancy

import com.pregnancy.domain.model.pregnancy.Fetus
import com.pregnancy.domain.repository.PregnancyRepository
import javax.inject.Inject

class GetMyFetusesUseCase @Inject constructor(
    private val pregnancyRepository: PregnancyRepository
) {
    suspend operator fun invoke(): Result<List<Fetus>> {
        return pregnancyRepository.getMyFetuses()
    }
}