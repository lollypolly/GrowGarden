package ru.itis.polypollya.growgarden.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_plants.*
import ru.itis.polypollya.growgarden.R
import ru.itis.polypollya.growgarden.adapters.FlowersViewPagerAdapter

class MyPlantsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_my_plants, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowers_view_pager.adapter = FlowersViewPagerAdapter(activity as AppCompatActivity)
        TabLayoutMediator(tab_layout, flowers_view_pager) { tab, position ->
            when (position) {
                0 -> tab.text = "All"
                1 -> tab.text = "Indoor"
                2 -> tab.text = "Outdoor"
            }
        }.attach()
    }
}