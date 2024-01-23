package com.a3tecnology.appmovie.presenter.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a3tecnology.appmovie.databinding.GenreItemBinding
import com.a3tecnology.appmovie.presenter.main.model.GenrePresentation

class GenreAdapter: ListAdapter<GenrePresentation, GenreAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object: DiffUtil.ItemCallback<GenrePresentation>() {
            override fun areItemsTheSame(
                oldItem: GenrePresentation,
                newItem: GenrePresentation
            ): Boolean {
                return oldItem.id ==  newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GenrePresentation,
                newItem: GenrePresentation
            ): Boolean {
                return oldItem ==  newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            GenreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val genre = getItem(position)

        holder.binding.txtNameCategory.text = genre.name

        val movieAdapter = MovieAdapter(holder.binding.root.context)
        val linearLayoutManager =
            LinearLayoutManager(
                holder.binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        holder.binding.recyclerMovies.layoutManager = linearLayoutManager
        holder.binding.recyclerMovies.setHasFixedSize(true)
        holder.binding.recyclerMovies.adapter = movieAdapter
        movieAdapter.submitList(genre.movies)

    }

    inner class MyViewHolder(val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}