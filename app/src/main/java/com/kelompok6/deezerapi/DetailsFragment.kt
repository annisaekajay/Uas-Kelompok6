package com.kelompok6.deezerapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kelompok6.deezerapi.Adapter.DetailsAdapter
import com.kelompok6.deezerapi.ViewModel.DeezerViewModel
import com.kelompok6.deezerapi.databinding.FragmentDetailsBinding
import com.kelompok6.deezerapi.models.Details
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: DetailsAdapter
    private val viewModel : DeezerViewModel by viewModels()
    var gelenid = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.rcylerDetails.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)

        arguments?.let {

            gelenid = DetailsFragmentArgs.fromBundle(it).id

        }

        InitRecycler()
        fetchCategory(gelenid)

        viewModel.getDetailsData().observe(viewLifecycleOwner, object : Observer<Details> {
            override fun onChanged(t: Details?) {

                if(t != null){
                    adapter.setLists(t.data)
                }
            }
        })

        return view
    }
    fun InitRecycler(){

        adapter = DetailsAdapter()
        binding.rcylerDetails.adapter = adapter

    }
    fun fetchCategory(idArtist: Int){
        CoroutineScope(Dispatchers.Main).async {
            viewModel.loadDetails(gelenid)
        }
    }
}