package com.a3tecnology.appmovie.presenter.main.moviedetails.details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.DialogDownloadingBinding
import com.a3tecnology.appmovie.databinding.FragmentMovieDetailsBinding
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.presenter.main.moviedetails.adapter.CastAdapter
import com.a3tecnology.appmovie.presenter.main.moviedetails.adapter.ViewPagerAdapter
import com.a3tecnology.appmovie.presenter.main.moviedetails.comments.CommentsFragment
import com.a3tecnology.appmovie.presenter.main.moviedetails.similar.SimilarFragment
import com.a3tecnology.appmovie.presenter.main.moviedetails.trailers.TrailersFragment
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.ViewPager2ViewHeightAnimator
import com.a3tecnology.appmovie.util.calculateFileSize
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

    private val viewMovieDetails: MovieDetailsViewModel by activityViewModels()

    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var castAdapter: CastAdapter

    private lateinit var dialogDownloading: AlertDialog

    private var movie: Movie? = null

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
        initListeners()
    }

    private fun initListeners() {
        binding.btnDownload.setOnClickListener { showDialogDownloading() }
    }

    private fun configTabLayout() {

        // iremos acessar o fragments baseado nos id
        viewMovieDetails.setMovieId(movieId = args.movieId)

        val adapter = ViewPagerAdapter(requireActivity())
        val mViewPager = ViewPager2ViewHeightAnimator()

        mViewPager.viewPager2 = binding.viewPager
        mViewPager.viewPager2?.adapter = adapter

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

        mViewPager.viewPager2?.let { viewPager ->
            TabLayoutMediator(
                binding.tabs, viewPager
            ) { tab, position ->
                tab.text = getString(adapter.getTitle(position))
            }.attach()
        }
    }

    private fun getMovieDetails() {

        viewMovieDetails.getMovieDetails(args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
//                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
//                    binding.progressBar.isVisible = false
                    this.movie = stateView.data
                    configData()
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

    private fun configData() {

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

    private fun showDialogDownloading() {
        val dialogBinding = DialogDownloadingBinding.inflate(LayoutInflater.from(requireContext()))

        var progress = 0
        var downloaded = 0.0
        val movieDuration =  movie?.runtime?.toDouble() ?: 0.0

        //bar progress and TextView progress
        val handle = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (progress < 100) {

                    downloaded += (movieDuration / 100.0)
                    dialogBinding.txtDownloading.text = getString(
                        R.string.txt_downloaded_size_dialog_downloading,
                        downloaded.calculateFileSize(),
                        movieDuration.calculateFileSize()
                    )

                    progress++
                    dialogBinding.lineProgress.progress = progress
                    dialogBinding.txtNumberProgress.text = getString(
                        R.string.txt_download_progress_dialog_downloading,
                        progress
                    )

                    handle.postDelayed(this, 50)
                } else {
                    dialogDownloading.dismiss()
                }

            }
        }

        handle.post(runnable)

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        builder.setView(dialogBinding.root)

        dialogBinding.btnDownloadHide.setOnClickListener { dialogDownloading.dismiss() }
        dialogBinding.imageBtnCancel.setOnClickListener { dialogDownloading.dismiss() }

        dialogDownloading = builder.create()
        dialogDownloading.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}