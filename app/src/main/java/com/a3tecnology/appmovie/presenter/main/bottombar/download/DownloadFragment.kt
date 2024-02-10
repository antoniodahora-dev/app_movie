package com.a3tecnology.appmovie.presenter.main.bottombar.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.a3tecnology.appmovie.MainGraphDirections
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentDownloadBinding
import com.a3tecnology.appmovie.presenter.main.bottombar.download.adapter.DownloadMovieAdapter
import com.a3tecnology.appmovie.util.hideKeyboard
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadFragment : Fragment() {

    private var _binding : FragmentDownloadBinding? = null
    private val binding get() = _binding!!

    private lateinit var downloadAdapter: DownloadMovieAdapter

    private val downloadViewModel: DownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        getObservers()
        getData()
        initSearchView()
    }

    private fun getData() {
        downloadViewModel.getMovie()
    }
    private fun getObservers() {
        downloadViewModel.movieList.observe(viewLifecycleOwner) { movies ->
            downloadAdapter.submitList(movies)
        }
    }

    private fun initRecycler() {

        downloadAdapter = DownloadMovieAdapter(
           context = requireContext(),
            detailsClickListener = { movieId ->

                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)

                    findNavController().navigate(action)
                }
            } ,

            deleteClickListener = { movieId ->

            }
        )

        with(binding.recyclerDownload) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = downloadAdapter
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                hideKeyboard()

                if (query.isNotEmpty()) {
//                    searchMovie(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.searchView.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
//                getMovieGenre()
            }

            override fun onSearchViewClosedAnimation() {}

            override fun onSearchViewShown() {}

            override fun onSearchViewShownAnimation() {}
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}