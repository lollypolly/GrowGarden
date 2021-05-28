package ru.itis.polypollya.growgarden.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.itis.polypollya.growgarden.R
import ru.itis.polypollya.growgarden.listeners.FlowerListener
import ru.itis.polypollya.growgarden.models.Flower

class AllPlantsAdapter : RecyclerView.Adapter<AllPlantsAdapter.AllPlantsHolder>() {

    var listOfFlowers: List<Flower> = arrayListOf()
    private var flowerListener: FlowerListener? = null

    inner class AllPlantsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flowerImg: ImageView = itemView.findViewById(R.id.flower_image)
        val flowerName: TextView = itemView.findViewById(R.id.name_flower)
    }

    fun setFlowerListener(flowerListener: FlowerListener) {
        this.flowerListener = flowerListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPlantsHolder =
        AllPlantsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_flower, parent, false)
        )

    override fun onBindViewHolder(holder: AllPlantsHolder, position: Int) {
        val flower = listOfFlowers[position]
        if (flower.imgPath.isNotEmpty()) holder.flowerImg.setImageURI(Uri.parse(flower.imgPath))
        holder.flowerName.text = flower.name
        holder.itemView.setOnClickListener { flowerListener?.onFlowerClicked(flower, position) }
    }

    override fun getItemCount(): Int = listOfFlowers.size

    fun submitList(listOfFlowers: List<Flower>) {
        this.listOfFlowers = listOfFlowers
        notifyDataSetChanged()
    }

}