package com.zubair.kotlinjetpack.model

import com.google.gson.annotations.SerializedName

data class DogBreed(
    @SerializedName("id")
    val id : String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("life_span")
    val lifespan: String?,

    @SerializedName("breed_group")
    val group: String?,

    @SerializedName("breed_for")
    val purpose: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @SerializedName("url")
    val url: String

)