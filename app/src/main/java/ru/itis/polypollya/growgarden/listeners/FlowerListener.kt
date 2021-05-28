package ru.itis.polypollya.growgarden.listeners

import ru.itis.polypollya.growgarden.models.Flower

interface FlowerListener {
    fun onFlowerClicked(flower: Flower, position: Int)
}