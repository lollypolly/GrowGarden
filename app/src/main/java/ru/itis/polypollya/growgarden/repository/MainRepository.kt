package ru.itis.polypollya.growgarden.repository

import ru.itis.polypollya.growgarden.data.FlowersDAO
import ru.itis.polypollya.growgarden.models.Flower
import javax.inject.Inject

class MainRepository @Inject constructor(
    var flowersDAO: FlowersDAO
) {

    suspend fun insertFlower(flower: Flower) = flowersDAO.insertFlower(flower)

    suspend fun updateFlower(flower: Flower) = flowersDAO.updateFlower(flower)

    fun getAllFlowers() = flowersDAO.getAllFlowers()

    fun getAllIndoorFlowers() = flowersDAO.getIndoorFlowers()

    fun getAllOutdoorFlowers() = flowersDAO.getOutdoorFlowers()
}