package com.a3tecnology.appmovie.data.mapper

import com.a3tecnology.appmovie.data.local.entity.MovieEntity
import com.a3tecnology.appmovie.data.model.movie.AuthorDetailsResponse
import com.a3tecnology.appmovie.data.model.movie.CountryResponse
import com.a3tecnology.appmovie.data.model.movie.CreditResponse
import com.a3tecnology.appmovie.data.model.movie.GenreResponse
import com.a3tecnology.appmovie.data.model.movie.MovieResponse
import com.a3tecnology.appmovie.data.model.movie.MovieReviewResponse
import com.a3tecnology.appmovie.data.model.movie.PersonResponse
import com.a3tecnology.appmovie.domain.model.movie.AuthorDetails
import com.a3tecnology.appmovie.domain.model.movie.Country
import com.a3tecnology.appmovie.domain.model.movie.Credit
import com.a3tecnology.appmovie.domain.model.movie.Genre
import com.a3tecnology.appmovie.domain.model.movie.Movie
import com.a3tecnology.appmovie.domain.model.movie.MovieReview
import com.a3tecnology.appmovie.domain.model.movie.Person

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
        genres = genres?.map { it.toDomain() },
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
        voteCount = voteCount,
        productionCountries = productionCountries?.map { it.toDomain() },
        runtime = runtime

    )
}

//fun Genre.toPresentation() : MoviesByGenre {
//
//    return MoviesByGenre(
//        id = id,
//        name = name,
//        movies = emptyList()
//
//    )
//}

fun CountryResponse.toDomain() : Country {
    return Country(
        name = name
    )
}

fun PersonResponse.toDomain() : Person {
    return Person(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath,
        castId = castId,
        character = character,
        creditId = creditId,
        order = order
    )
}

fun CreditResponse.toDomain(): Credit {
    return Credit(
        cast = cast?.map { it.toDomain() }
    )
}

fun AuthorDetailsResponse.toDomain(): AuthorDetails {
    return AuthorDetails(
        name = name,
        username = username,
        avatarPath = avatarPath,
        rating = rating ,
    )
}

fun MovieReviewResponse.toDomain(): MovieReview {

    return MovieReview(
        author = author,
        authorDetails = authorDetailsResponse?.toDomain(),
        createdAt = createdAt,
        id =  id,
        content = content,
        updatedAt = updatedAt,
        url = url
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        poster =  posterPath,
        runtime = runtime,
        insertion = System.currentTimeMillis()
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath =  poster,
        runtime = runtime
    )
}




