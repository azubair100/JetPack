package com.zubair.kotlinjetpack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "dog_breed")
data class DogBreed(
    @ColumnInfo(name = "api_id")
    @SerializedName("id")
    val id : String?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "lifespan")
    @SerializedName("life_span")
    val lifespan: String?,

    @ColumnInfo(name = "group")
    @SerializedName("breed_group")
    val group: String?,

    @ColumnInfo(name = "purpose")
    @SerializedName("breed_for")
    val purpose: String?,

    @ColumnInfo(name = "temperament")
    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String
){
    /*
    Since all these api retrieved data is string, I want to have an unique id every time I
    put in a new row in the mobile's database, this this primary key code

    data classes don't necessarily need a body but for the auto generate id we need it
    */

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}