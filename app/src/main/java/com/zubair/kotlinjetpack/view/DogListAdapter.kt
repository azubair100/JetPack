package com.zubair.kotlinjetpack.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.zubair.kotlinjetpack.R
import com.zubair.kotlinjetpack.model.DogBreed
import kotlinx.android.synthetic.main.dog_row.view.*

class DogListAdapter(private val dogList: ArrayList<DogBreed>): RecyclerView.Adapter<DogListAdapter.DogViewHolder>() {

    fun updateDogList(newList : List<DogBreed>){
        dogList.clear()
        dogList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder =
        DogViewHolder(LayoutInflater.from(parent.context).
            inflate(R.layout.dog_row, parent, false))

    override fun getItemCount(): Int = dogList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) = holder.bind(dogList[position])

    class DogViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(dog: DogBreed) {
            itemView.name.text = dog.name
            itemView.lifespan.text = dog.lifespan
            itemView.setOnClickListener{
                Navigation.findNavController(it).navigate(ListFragmentDirections.navigateToDetailFragment())
            }
        }
    }

}