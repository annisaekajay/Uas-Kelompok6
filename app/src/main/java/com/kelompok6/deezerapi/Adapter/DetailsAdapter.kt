package com.kelompok6.deezerapi.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kelompok6.deezerapi.DetailsFragmentDirections
import com.kelompok6.deezerapi.databinding.CategoryItemBinding
import com.kelompok6.deezerapi.models.Data
import com.bumptech.glide.Glide

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.MyMusic>() {

    var liveData : List<Data>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMusic {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyMusic(binding)
    }


    override fun onBindViewHolder(holder: MyMusic, position: Int) {
        holder.bind(liveData!![position])
        val binding = holder.binding.gridLayout
        binding.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToArtistsFragment(liveData!![position].id)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        if(liveData == null){
            return 0
        }else{
            return liveData!!.size
        }
    }

    inner class MyMusic(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.textView.text =  data.name
            Glide.with(binding.categoryimg)
                .load(data.picture)
                .into(binding.categoryimg)
        }
    }
    fun setLists(list: List<Data>?) {
            this.liveData = list
            notifyDataSetChanged()

    }
}