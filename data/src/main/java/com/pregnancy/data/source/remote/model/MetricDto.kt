package com.pregnancy.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class MetricDto(
    @SerializedName("metricId")
    val id: Long
) {
}