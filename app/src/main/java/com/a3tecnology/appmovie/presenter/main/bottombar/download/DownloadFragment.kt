package com.a3tecnology.appmovie.presenter.main.bottombar.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a3tecnology.appmovie.MainGraphDirections
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.BottomSheetDeleteMovieBinding
import com.a3tecnology.appmovie.databinding.FragmentDownloadBinding
import com.a3tecnology.appmovie.domain.model.movie.Movie
import com.a3tecnology.appmovie.presenter.main.bottombar.download.adapter.DownloadMovieAdapter
import com.a3tecnology.appmovie.util.calculateFileSize
import com.a3tecnology.appmovie.util.calculateMovieTime
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.onNavigate
import com.bumptech.glide.Glide
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        initToolbar(toolbar = binding.toolbar, showIconNavigation = false)

        initRecycler()
        initObservers()
        getData()
        initSearchView()
        initListeners()
    }

    private fun getData() {
        downloadViewModel.getMovie()
    }

    private fun initObservers() {
        downloadViewModel.movieList.observe(viewLifecycleOwner) { movies ->
            downloadAdapter.submitList(movies)
            emptyState(empty = movies.isEmpty())
        }

        downloadViewModel.movieSearchList.observe(viewLifecycleOwner) { movies ->
            downloadAdapter.submitList(movies)
            emptyState(empty = movies.isEmpty())
        }
    }

    private fun initListeners() {
        initSearchView()
        onBackPressed()
    }

    private fun initRecycler() {

        downloadAdapter = DownloadMovieAdapter(
           context = requireContext(),
            detailsClickListener = { movieId ->

                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)

                    findNavController().onNavigate(action)
                }
            } ,

            deleteClickListener = { movie ->
                showBottomSheetDeleteMovie(movie)
            }
        )

        with(binding.recyclerDownload) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = downloadAdapter
        }
    }

    private fun showBottomSheetDeleteMovie(movie: Movie?) {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetDeleteMovieBinding.inflate(
            layoutInflater, null, false)


        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w200${movie?.posterPath}")
            .into(bottomSheetBinding.ivMovie)

        bottomSheetBinding.textMovie.text = movie?.title
        bottomSheetBinding.textDuration.text = movie?.runtime?.calculateMovieTime()
        bottomSheetBinding.textSize.text = movie?.runtime?.toDouble()?.calculateFileSize()


        bottomSheetBinding.btnCancel.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetBinding.btnConfirm.setOnClickListener {
            bottomSheetDialog.dismiss()
            downloadViewModel.deleteMovie(movie?.id)
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun emptyState(empty: Boolean) {
        binding.recyclerDownload.isVisible = !empty
        binding.linearLayoutNotFound.isVisible = empty

    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                   if (binding.searchView.isVisible) {
                       binding.searchView.closeSearch()
                   } else {
                       findNavController().popBackStack()
                   }
                }
            })
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText.isNotBlank() || newText.isEmpty()) {
                    downloadViewModel.searchMovie(newText)
                }
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.searchView.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                downloadViewModel.getMovie()
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