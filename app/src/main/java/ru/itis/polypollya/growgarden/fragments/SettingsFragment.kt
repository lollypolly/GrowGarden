package ru.itis.polypollya.growgarden.fragments

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.itis.polypollya.growgarden.R
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject lateinit var mainPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterPeriod: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(
                requireActivity().applicationContext,
                R.array.periods,
                android.R.layout.simple_spinner_item
            )
        adapterPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_period.adapter = adapterPeriod

        spinner_period.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.periods)
                mainPrefs.edit().putInt(
                    "period_pushes",
                    choose[selectedItemPosition].substring(0, 1).toInt()
                ).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        switch_pushes.setOnClickListener {
            if (switch_pushes.isChecked) {
                mainPrefs.edit().putBoolean(
                    "is_push_enabled",
                    true
                ).apply()
            } else {
                mainPrefs.edit().putBoolean(
                    "is_push_enabled",
                    false
                ).apply()
            }
        }
    }
}