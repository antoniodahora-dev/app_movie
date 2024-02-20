package com.a3tecnology.appmovie.presenter.main.moviegenre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.a3tecnology.appmovie.MainGraphDirections
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentMovieGenreBinding
import com.a3tecnology.appmovie.presenter.main.moviegenre.adapter.LoadStatePagingAdapter
import com.a3tecnology.appmovie.presenter.main.moviegenre.adapter.MoviePagingAdapter
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.hideKeyboard
import com.a3tecnology.appmovie.util.initToolbar
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private var _binding: FragmentMovieGenreBinding? = null
    private val binding get() = _binding!!

    private val args: MovieGenreFragmentArgs by navArgs()

    private val movieGenreViewModel: MovieGenreViewModel by viewModels()

    private lateinit var moviePagingAdapter: MoviePagingAdapter

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
        getByMovieGenre()
        initSearchView()
    }

    private fun initRecycler() {

        moviePagingAdapter = MoviePagingAdapter(
            requireContext(),
            movieClickListener = { movieId ->

                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)

                    findNavController().navigate(action)
                }
            }
        )

        lifecycleScope.launch {
            moviePagingAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.recyclerMovies.isVisible = false
                        binding.shimmer.startShimmer()
                        binding.shimmer.isVisible = true
//                        binding.progressBar.isVisible = true
                    }
                    is LoadState.NotLoading -> {
                        binding.shimmer.stopShimmer()
                        binding.recyclerMovies.isVisible = true
                        binding.shimmer.isVisible = false
//                        binding.progressBar.isVisible = false
                    }
                    is LoadState.Error ->  {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.isVisible = false
                        binding.recyclerMovies.isVisible = false
//                        binding.progressBar.isVisible = false

                        val error = (loadState.refresh as LoadState.Error).error.message ?:
                        "Ocorreu um erro. Tente novamente mais tarde!"
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.recyclerMovies) {
            setHasFixedSize(true)

            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager = gridLayoutManager

            val footerAdapter = moviePagingAdapter.withLoadStateFooter(
                footer = LoadStatePagingAdapter()
            )

            adapter = footerAdapter

            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == moviePagingAdapter.itemCount
                        && footerAdapter.itemCount > 0) {
                        2
                    } else {
                        1
                    }
                }
            }

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
                getByMovieGenre()
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

    private fun getByMovieGenre(forceRequest: Boolean = false) {

        lifecycleScope.launch {
            movieGenreViewModel.getMovieByGenre(genreId = args.genreId, forceRequest = forceRequest)
            movieGenreViewModel.movieList.collectLatest { pagingData ->
                moviePagingAdapter.submitData(pagingData)
            }
        }

    }

    private fun searchMovie(query: String?) {

        lifecycleScope.launch {
            movieGenreViewModel.searchMovie(query).collectLatest { pagingData ->
                moviePagingAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}