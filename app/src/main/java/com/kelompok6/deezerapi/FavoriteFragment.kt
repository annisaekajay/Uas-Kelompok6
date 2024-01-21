package com.kelompok6.deezerapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.kelompok6.deezerapi.Adapter.FavoriteRecyclerAdapter
import com.kelompok6.deezerapi.Room.DataDao
import com.kelompok6.deezerapi.Room.RoomDataBase
import com.kelompok6.deezerapi.databinding.FragmentFavoriteBinding
import com.kelompok6.deezerapi.models.album.favoriteMusic

class FavoriteFragment : Fragment() {


    private lateinit var musicList : List<favoriteMusic>
    private lateinit var FavoriAdapter : FavoriteRecyclerAdapter
    private lateinit var db: RoomDataBase
    private lateinit var adventDao: DataDao
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(requireContext().applicationContext, RoomDataBase::class.java, "Music")
            .allowMainThreadQueries()
            .build()
        adventDao = db.dataDao()

        val recyclerViewAdapter = FavoriteRecyclerAdapter(adventDao.getAll() as ArrayList<favoriteMusic>)
        binding.FavoriteRecyler.adapter = recyclerViewAdapter

        binding.FavoriteRecyler.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        var _binding = null
    }
}
