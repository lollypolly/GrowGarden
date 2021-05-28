package ru.itis.polypollya.growgarden.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_indoor_plants.*
import ru.itis.polypollya.growgarden.R
import ru.itis.polypollya.growgarden.UpdateFlowerActivity
import ru.itis.polypollya.growgarden.adapters.IndoorPlantsAdapter
import ru.itis.polypollya.growgarden.listeners.FlowerListener
import ru.itis.polypollya.growgarden.models.Flower
import ru.itis.polypollya.growgarden.viewmodel.FlowerViewModel
import javax.inject.Inject

@AndroidEntryPoint
class IndoorPlantsFragment : Fragment(), FlowerListener {

    @Inject
    lateinit var indoorPlantsAdapter: IndoorPlantsAdapter

    private val flowersViewModel: FlowerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_indoor_plants, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        indoorPlantsAdapter.setFlowerListener(this)
        indoor_recycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = indoorPlantsAdapter
        }
        flowersViewModel.allIndoorFlowers.observe(viewLifecycleOwner, {
            indoorPlantsAdapter.submitList(it)
        })
    }

    override fun onFlowerClicked(flower: Flower, position: Int) {
        val intent = Intent(context, UpdateFlowerActivity::class.java)
        intent.putExtra("flower", flower)
        startActivity(intent)
    }
}