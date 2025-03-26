package com.pregnancy.data.source.remote.api

import com.pregnancy.data.source.remote.ApiConstants
import com.pregnancy.data.source.remote.model.ApiResponse
import com.pregnancy.data.source.remote.model.pregnancy.FetusDto
import com.pregnancy.data.source.remote.model.pregnancy.PregnancyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PregnancyApiService {

    @GET("${ApiConstants.PREGNANCY_PATH}/me/current")
    suspend fun getMyOnGoingPregnancy(): Response<ApiResponse<PregnancyDto>>

    @GET("${ApiConstants.PREGNANCY_PATH}/me/current/insight")
    suspend fun getOnGoingPregnancyWeekInsight(): Response<ApiResponse<String>>

    @GET("${ApiConstants.PREGNANCY_PATH}/me/current/fetuses")
    suspend fun getMyFetuses(): Response<ApiResponse<List<FetusDto>>>
}