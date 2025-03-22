package com.pregnancy.data.repository

import com.pregnancy.data.mapper.toDomain
import com.pregnancy.data.source.remote.api.PregnancyApiService
import com.pregnancy.data.source.remote.parseErrorResponse
import com.pregnancy.domain.model.pregnancy.Pregnancy
import com.pregnancy.domain.repository.PregnancyRepository
import javax.inject.Inject

class PregnancyRepositoryImpl @Inject constructor(
    private val apiService: PregnancyApiService
) : PregnancyRepository {

    override suspend fun getMyOnGoingPregnancy(): Result<Pregnancy> {
        return try {
            val response = apiService.getMyOnGoingPregnancy()
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    val pregnancyDto = res.data
                    Result.success(pregnancyDto.toDomain())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGestationalWeekInsight(): Result<String> {
        return try {
            val response = apiService.getOnGoingPregnancyWeekInsight()
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    val data = res.data
                    Result.success(data)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}