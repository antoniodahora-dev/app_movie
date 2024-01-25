package com.a3tecnology.appmovie.data.mapper

import com.a3tecnology.appmovie.data.model.GenreResponse
import com.a3tecnology.appmovie.data.model.MovieResponse
import com.a3tecnology.appmovie.domain.model.Genre
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.presenter.main.bottombar.model.GenrePresentation

fun GenreResponse.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun MovieResponse.toDomain(): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun Genre.toPresentation() : GenrePresentation {

    return GenrePresentation(
        id = id,
        name = name,
        movies = emptyList()

    )
}