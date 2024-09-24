package com.a3tecnology.appmovie.presenter.main.bottombar.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.ItemUserProfileBinding
import com.a3tecnology.appmovie.domain.model.menu.MenuProfile
import com.a3tecnology.appmovie.domain.model.menu.MenuProfileType

// aula 363
class ProfileMenuAdapter(
    private val items: List<MenuProfile>,
    private val context: Context,
    private val onClick: (MenuProfileType) -> Unit
) : RecyclerView.Adapter<ProfileMenuAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(
           ItemUserProfileBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
       )
    }

    override fun getItemCount(): Int {
      return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val item = items[position]

        holder.binding.txtItemProfile.apply {
            text = context.getString(item.text)
            if (item.type == MenuProfileType.LOGOUT) {
                setTextColor(ContextCompat.getColor(context, R.color.color_default))
            }
        }

        holder.binding.imgItemProfile.setImageDrawable(
            ContextCompat.getDrawable(context, item.icon)
        )
        holder.itemView.setOnClickListener {
            onClick(item.type)
        }
    }

    inner class MyViewHolder(val binding: ItemUserProfileBinding) :
        RecyclerView.ViewHolder(binding.root)
}