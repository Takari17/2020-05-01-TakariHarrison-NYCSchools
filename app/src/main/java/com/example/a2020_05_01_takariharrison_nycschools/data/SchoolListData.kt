package com.example.a2020_05_01_takariharrison_nycschools.data

import com.google.gson.annotations.SerializedName

data class SchoolListData(
    @SerializedName("school_name")
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("school_email")
    val email: String,
    val website: String,
    val city: String
)