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
import kotlinx.android.synthetic.main.fragment_all_plants.*
import ru.itis.polypollya.growgarden.R
import ru.itis.polypollya.growgarden.UpdateFlowerActivity
import ru.itis.polypollya.growgarden.adapters.AllPlantsAdapter
import ru.itis.polypollya.growgarden.listeners.FlowerListener
import ru.itis.polypollya.growgarden.models.Flower
import ru.itis.polypollya.growgarden.viewmodel.FlowerViewModel
import javax.inject.Inject


@AndroidEntryPoint
class AllPlantsFragment : Fragment(), FlowerListener {

    @Inject lateinit var allPlantsAdapter: AllPlantsAdapter

    private val flowersViewModel: FlowerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_plants, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allPlantsAdapter.setFlowerListener(this)
        all_plants_recycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = allPlantsAdapter
        }
        flowersViewModel.allFlowers.observe(viewLifecycleOwner, {
            allPlantsAdapter.submitList(it)
        })
    }

    override fun onFlowerClicked(flower: Flower, position: Int) {
        val intent = Intent(context, UpdateFlowerActivity::class.java)
        intent.putExtra("flower", flower)
        startActivity(intent)
    }
}