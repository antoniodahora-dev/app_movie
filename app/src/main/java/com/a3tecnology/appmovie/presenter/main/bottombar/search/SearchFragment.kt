package com.a3tecnology.appmovie.presenter.main.bottombar.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.a3tecnology.appmovie.MainGraphDirections
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentSearchBinding
import com.a3tecnology.appmovie.presenter.main.bottombar.home.adapter.MovieAdapter
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initSearchView()
        initObservers()
    }

    private fun initObservers() {
        stateObserver()
        searchObserver()
    }

    private fun stateObserver() {

        searchViewModel.searchState.observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {
                    binding.recyclerMovies.isVisible = false
                    binding.progressBar.isVisible = true

                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerMovies.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun searchObserver() {

        searchViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            movieAdapter.submitList(movieList)
            emptyState(empty = movieList.isEmpty())
        }
    }

    private fun initRecycler() {

        movieAdapter = MovieAdapter(
            requireContext(),
            layoutInflater = R.layout.movie_genre_item,
            movieClickListener = { movieId ->

                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)

                    findNavController().navigate(action)
                }
            }
        )

        with(binding.recyclerMovies) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                hideKeyboard()

                if (query.isNotEmpty()) {
                    searchViewModel.searchMovie(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SimpleSearchView", "Text changed:$newText")
                return false
            }

        })


    }

    private fun emptyState(empty: Boolean) {
        binding.recyclerMovies.isVisible = !empty
        binding.linearLayoutNotFound.isVisible = empty

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}