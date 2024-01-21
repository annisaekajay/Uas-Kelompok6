package com.kelompok6.deezerapi.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompok6.deezerapi.*
import com.kelompok6.deezerapi.databinding.ArtistItemBinding
import com.kelompok6.deezerapi.models.artistX
import com.bumptech.glide.Glide

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.MyArtist>() {

    var liveData : List<artistX>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyArtist {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyArtist(binding)
    }


    override fun onBindViewHolder(holder: MyArtist, position: Int) {
        holder.bind(liveData!![position])

    }

    override fun getItemCount(): Int {
        if(liveData == null){
            return 0
        }else{
            return liveData!!.size
        }
    }

    inner class MyArtist(val binding: ArtistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: artistX) {

            binding.txtArtist.text = data.name
            Glide.with(binding.imgArtist)
                .load(data.picture_big)
                .into(binding.imgArtist)
        }
    }
    fun setLists(liveData: List<artistX>?) {
            this.liveData = liveData
            notifyDataSetChanged()
    }
}