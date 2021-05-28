package ru.itis.polypollya.growgarden.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.itis.polypollya.growgarden.models.Flower
@Dao
interface FlowersDAO {

    @Insert
    fun insertFlower(flower: Flower)

    @Update
    fun updateFlower(flower: Flower)

    @Query("SELECT * FROM flowers")
    fun getAllFlowers(): LiveData<List<Flower>>

    @Query("SELECT * FROM flowers WHERE type == 'indoor'")
    fun getIndoorFlowers(): LiveData<List<Flower>>

    @Query("SELECT * FROM flowers WHERE type == 'outdoor'")
    fun getOutdoorFlowers(): LiveData<List<Flower>>
}