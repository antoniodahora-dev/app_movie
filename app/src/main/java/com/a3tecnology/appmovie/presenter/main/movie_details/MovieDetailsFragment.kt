package com.a3tecnology.appmovie.presenter.main.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentMovieDetailsBinding
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.presenter.main.movie_details.adapter.CastAdapter
import com.a3tecnology.appmovie.presenter.main.movie_details.adapter.ViewPagerAdapter
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.initToolbar
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? =  null

    private val binding get() = _binding!!

    private val viewMovieDetails: MovieDetailsViewModel by viewModels()

    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var castAdapter: CastAdapter

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
        initRecyclerCredits()
        configTabLayout()
    }

    private fun configTabLayout() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        adapter.addFragment(
            fragment = TrailersFragment(),
            title = R.string.title_trailers_tab_layout
        )

        adapter.addFragment(
            fragment = SimilarFragment(),
            title = R.string.title_similar_tab_layout
        )

        adapter.addFragment(
            fragment = CommentsFragment(),
            title = R.string.title_Comments_tab_layout
        )

        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab, position ->
            tab.text = getString(adapter.getTitle(position))
        }.attach()


    }

    private fun getMovieDetails() {

        viewMovieDetails.getMovieDetails(args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
//                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
//                    binding.progressBar.isVisible = false
                    configData(movie = stateView.data)
                }
                is StateView.Error -> {
//                    binding.progressBar.isVisible = false
                }
            }

        }
    }

    private fun initRecyclerCredits() {

        castAdapter = CastAdapter()

        with(binding.recyclerCast) {
//            setHasFixedSize(true) - só devemos usar quando o tamnaho é fixo
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            adapter = castAdapter
        }
    }

    private fun getCredit() {

        viewMovieDetails.getCredit(args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
//                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
//                    binding.progressBar.isVisible = false
                    castAdapter.submitList(stateView.data?.cast )

                }
                is StateView.Error -> {
//                    binding.progressBar.isVisible = false
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

        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val data = originalFormat.parse(movie?.releaseDate ?: "")
        val yearFormat = SimpleDateFormat("yyyy", Locale.ROOT)
        val year = yearFormat.format(data)

        binding.txtAge.text = year
        binding.txtCountry.text = movie?.productionCountries?.get(0)?.name ?: ""

        val genre = movie?.genres?.map {it.name }?.joinToString(" , ")
        binding.txtGenres.text = getString(R.string.txt_all_genres_movie_detail, genre)

        binding.txtDescription.text = movie?.overview

        getCredit()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}