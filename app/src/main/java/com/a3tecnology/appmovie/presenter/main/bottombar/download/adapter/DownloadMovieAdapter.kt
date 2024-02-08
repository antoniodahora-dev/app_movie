package com.a3tecnology.appmovie.presenter.main.bottombar.download.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.MovieDownloadItemBinding
import com.a3tecnology.appmovie.databinding.MovieItemBinding
import com.a3tecnology.appmovie.domain.model.Movie
import com.bumptech.glide.Glide

class DownloadMovieAdapter(
    private val context: Context,
    private val detailsClickListener: (Int?) -> Unit,
    private val deleteClickListener: (Int?) -> Unit

): ListAdapter<Movie, DownloadMovieAdapter.MyViewHolder>(DIFF_UTIL) {

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
        return MyViewHolder(
            MovieDownloadItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = getItem(position)
        Glide
            .with(context)
            .load("https://image.tmdb.org/t/p/w200${movie.posterPath}")
//            .error(R.drawable.movie) - caso o item não contenha image
            .into(holder.binding.ivMovie)

        holder.binding.textMovie.text = movie.title
        holder.binding.textDuration.text = movie.runtime.toString()
        holder.binding.textSize.text = movie.runtime.toString()
        holder.itemView.setOnClickListener { detailsClickListener(movie.id) }
        holder.binding.ibDelete.setOnClickListener { deleteClickListener(movie.id) }
    }

    inner class MyViewHolder(val binding : MovieDownloadItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}