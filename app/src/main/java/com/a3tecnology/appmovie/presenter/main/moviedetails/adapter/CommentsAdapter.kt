package com.a3tecnology.appmovie.presenter.main.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.ItemCommentReviewBinding
import com.a3tecnology.appmovie.domain.model.MovieReview
import com.a3tecnology.appmovie.util.formatCommentDate
import com.bumptech.glide.Glide

class CommentsAdapter : ListAdapter<MovieReview, CommentsAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object: DiffUtil.ItemCallback<MovieReview>() {
            override fun areItemsTheSame(
                oldItem: MovieReview,
                newItem: MovieReview): Boolean {
                return oldItem.id ==  newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieReview,
                newItem: MovieReview): Boolean {
                return oldItem ==  newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

      return MyViewHolder(
          ItemCommentReviewBinding.inflate(
              LayoutInflater.from(parent.context),
              parent,
              false
          )
      )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val review = getItem(position)

        review.authorDetails?.avatarPath?.let { avatarPath ->
            Glide
                .with(holder.binding.root.context)
                .load("https://image.tmdb.org/t/p/w500$avatarPath")
                .into(holder.binding.imageUserComment)
        } ?: run {
            holder.binding.imageUserComment.setImageDrawable(
                ContextCompat.getDrawable(holder.binding.root.context, R.drawable.movie)
            )
        }


        holder.binding.txtUsernameComment.text = review.authorDetails?.name
        holder.binding.txtDetailComment.text = review.content
        holder.binding.txtRating.text = review?.authorDetails?.rating?.toString() ?: "0"
        holder.binding.txtDate.text = formatCommentDate(review.createdAt)

    }

    inner class MyViewHolder(val binding: ItemCommentReviewBinding) : RecyclerView.ViewHolder(binding.root)

}