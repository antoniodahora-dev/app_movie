package com.a3tecnology.appmovie.presenter.main.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a3tecnology.appmovie.databinding.CastItemBinding
import com.a3tecnology.appmovie.domain.model.movie.Person
import com.bumptech.glide.Glide

class CastAdapter : ListAdapter<Person, CastAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object: DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id ==  newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem ==  newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

      return MyViewHolder(
          CastItemBinding.inflate(
              LayoutInflater.from(parent.context),
              parent,
              false
          )
      )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val person = getItem(position)
        Glide
            .with(holder.binding.root.context)
            .load("https://image.tmdb.org/t/p/w500${person.profilePath}")
            .into(holder.binding.imageCast)

        holder.binding.txtPersonName.text = person.name
    }

    inner class MyViewHolder(val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root)

}