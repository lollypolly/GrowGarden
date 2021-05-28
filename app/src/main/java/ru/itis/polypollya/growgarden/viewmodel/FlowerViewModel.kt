package ru.itis.polypollya.growgarden.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.polypollya.growgarden.models.Flower
import ru.itis.polypollya.growgarden.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class FlowerViewModel @Inject constructor(
    var flowerRepo: MainRepository
): ViewModel() {

    val allFlowers = flowerRepo.getAllFlowers()
    val allIndoorFlowers = flowerRepo.getAllIndoorFlowers()
    val allOutdoorFlowers = flowerRepo.getAllOutdoorFlowers()

    fun insertFlower(flower: Flower) = CoroutineScope(Dispatchers.IO).launch {
        flowerRepo.insertFlower(flower)
    }

    fun updateFlower(flower: Flower) = CoroutineScope(Dispatchers.IO).launch {
        flowerRepo.updateFlower(flower)
    }
}