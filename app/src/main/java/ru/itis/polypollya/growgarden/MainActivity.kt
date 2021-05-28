package ru.itis.polypollya.growgarden

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iammert.library.readablebottombar.ReadableBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*
import ru.itis.polypollya.growgarden.fragments.AddPlantFragment
import ru.itis.polypollya.growgarden.fragments.MyPlantsFragment
import ru.itis.polypollya.growgarden.fragments.SettingsFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main)

        if (intent.hasExtra("fromReceiver")) {
            if (intent.getBooleanExtra("fromReceiver", false)) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, MyPlantsFragment())
                    .commit()
            }
        }

        if (intent.hasExtra("isFromUpdate")) {
            if (intent.getBooleanExtra("isFromUpdate", false)) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, MyPlantsFragment())
                    .commit()
            }
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MyPlantsFragment())
                .commit()
        }

        bottomBar.setOnItemSelectListener( object : ReadableBottomBar.ItemSelectListener{
            override fun onItemSelected(index: Int) {
                when (index) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, MyPlantsFragment())
                            .commit()
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, AddPlantFragment())
                            .commit()
                    }
                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, SettingsFragment())
                            .commit()
                    }
                }
            }
        })
    }
}