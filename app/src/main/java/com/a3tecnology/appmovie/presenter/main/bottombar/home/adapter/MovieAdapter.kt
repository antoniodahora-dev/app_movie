package com.a3tecnology.appmovie.presenter.main.bottombar.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.MovieItemBinding
import com.a3tecnology.appmovie.domain.model.Movie
import com.bumptech.glide.Glide

class MovieAdapter(
    private val context: Context,
    private val layoutInflater: Int
): ListAdapter<Movie, MovieAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id ==  newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem ==  newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(layoutInflater, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = getItem(position)
        Glide
            .with(context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
//            .error(R.drawable.movie) - caso o item não contenha image
            .into(holder.movieView)

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val movieView : ImageView

        init {
            movieView = itemView.findViewById(R.id.movie_image)
        }
    }
}