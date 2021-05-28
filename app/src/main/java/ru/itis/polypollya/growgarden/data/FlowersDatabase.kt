package ru.itis.polypollya.growgarden.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.itis.polypollya.growgarden.models.Flower

@Database(entities = [Flower::class], version = 1)
abstract class FlowersDatabase : RoomDatabase() {
    abstract fun getFlowersDao(): FlowersDAO
}