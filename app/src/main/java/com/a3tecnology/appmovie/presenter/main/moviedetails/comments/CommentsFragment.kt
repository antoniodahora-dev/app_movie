package com.a3tecnology.appmovie.presenter.main.moviedetails.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.a3tecnology.appmovie.databinding.FragmentCommentsBinding
import com.a3tecnology.appmovie.presenter.main.moviedetails.adapter.CommentsAdapter
import com.a3tecnology.appmovie.presenter.main.moviedetails.details.MovieDetailsViewModel
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null

    private val binding get() = _binding!!

    private lateinit var commentsAdapter: CommentsAdapter

    private val commentsViewModel: CommentsViewModel by viewModels()

    private val viewMovieDetails: MovieDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCommentsBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initObservers()

    }

    private fun initRecycler() {
        commentsAdapter = CommentsAdapter()

        with(binding.recyclerVComment) {
            adapter = commentsAdapter
        }
    }

    private fun getMovieReviews(movieId: Int) {

        commentsViewModel.getMovieReviews(movieId).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    commentsAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {}
            }
        }
    }

    private fun initObservers() {
        viewMovieDetails.movieId.observe(viewLifecycleOwner) { movieId ->
            if (movieId > 0 ) {
                getMovieReviews(movieId)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}