package com.a3tecnology.appmovie.presenter.main.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.a3tecnology.appmovie.databinding.FragmentMovieDetailsBinding
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.initToolbar
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? =  null

    private val binding get() = _binding!!

    private val viewMovieDetails: MovieDetailsViewModel by viewModels()

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar, lightIcon = true)
        getMovieDetails()
    }

    private fun getMovieDetails() {

        viewMovieDetails.getMovieDetails(args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    configData(movie = stateView.data)
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }

        }
    }

    private fun configData(movie: Movie?) {

        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
            .into(binding.imagePostMovie)

        binding.txtTitleMovieDetail.text = movie?.title

        binding.txtVoteAverage.text = String.format("%.1f", movie?.voteAverage)
        binding.txtCountry.text = movie?.productionCountries?.get(0)?.name ?: ""

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}