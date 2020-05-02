package com.example.a2020_05_01_takariharrison_nycschools.data

import com.google.gson.annotations.SerializedName

data class SchoolSatData(
    @SerializedName("sat_critical_reading_avg_score")
    val readingScore: String,
    @SerializedName("sat_math_avg_score")
    val mathScore: String,
    @SerializedName("sat_writing_avg_score")
    val writingScore: String
)
