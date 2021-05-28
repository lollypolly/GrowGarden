package ru.itis.polypollya.growgarden.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "flowers")
data class Flower(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val name: String,
    val desc: String,
    val imgPath: String,
    val type: String): Serializable