package com.zubair.kotlinjetpack.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDAO {

    /*
    * vararg multiple arguments, many objects of DogBreeds
    * It will return list of new dogs inserted with the uuid
    * suspend means insertAll fun will happen in the background
    */
    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dog_breed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dog_breed WHERE uuid = :dogId")
    suspend fun getDogById(dogId: Int): DogBreed
    
    @Query("DELETE FROM dog_breed")
    suspend fun deleteAllDogs()
}