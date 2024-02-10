package com.a3tecnology.appmovie.presenter.main.moviegenre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.a3tecnology.appmovie.MainGraphDirections
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentMovieGenreBinding
import com.a3tecnology.appmovie.presenter.main.bottombar.home.adapter.MovieAdapter
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.hideKeyboard
import com.a3tecnology.appmovie.util.initToolbar
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private var _binding: FragmentMovieGenreBinding? = null
    private val binding get() = _binding!!

    private val args: MovieGenreFragmentArgs by navArgs()

    private val movieGenreViewModel: MovieGenreViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar)
        binding.txtTitle.text = args.name

        initRecycler()
        getMovieGenre()
        initSearchView()
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
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                hideKeyboard()

                if (query.isNotEmpty()) {
                    searchMovie(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SimpleSearchView", "Text changed:$newText")
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.d("SimpleSearchView", "Text cleared")
                return false
            }
        })

        binding.searchView.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                getMovieGenre()
            }

            override fun onSearchViewClosedAnimation() {
                Log.i("SimpleSearchView", "onSearchViewShown")
            }

            override fun onSearchViewShown() {
                Log.i("SimpleSearchView", "onSearchViewShown")
            }

            override fun onSearchViewShownAnimation() {
                Log.i("SimpleSearchView", "onSearchViewShown")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getMovieGenre() {

        movieGenreViewModel.getMovieByGenre(args.genreId).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {
                    binding.recyclerMovies.isVisible = false
                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    movieAdapter.submitList(stateView.data)
                    binding.recyclerMovies.isVisible = true
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun searchMovie(query: String?) {

        movieGenreViewModel.searchMovie(query).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {
                    binding.recyclerMovies.isVisible = false
                    binding.progressBar.isVisible = true

                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    movieAdapter.submitList(stateView.data)
                    binding.recyclerMovies.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}