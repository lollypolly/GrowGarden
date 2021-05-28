package ru.itis.polypollya.growgarden.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.itis.polypollya.growgarden.fragments.AllPlantsFragment
import ru.itis.polypollya.growgarden.fragments.IndoorPlantsFragment
import ru.itis.polypollya.growgarden.fragments.OutdoorPlantsFragment

class FlowersViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
            0 -> AllPlantsFragment()
            1 -> IndoorPlantsFragment()
            2 -> OutdoorPlantsFragment()
            else -> AllPlantsFragment()
    }
}