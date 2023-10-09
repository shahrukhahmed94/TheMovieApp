package com.shahrukh.movieapp.screens

import android.widget.RatingBar
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.Reviews
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shahrukh.movieapp.R
import com.shahrukh.movieapp.model.Cast
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.model.Genre
import com.shahrukh.movieapp.sharedComposables.BackButton
import com.shahrukh.movieapp.sharedComposables.ExpandableText
import com.shahrukh.movieapp.sharedComposables.MovieGenreChip
import com.shahrukh.movieapp.ui.theme.AppOnPrimaryColor
import com.shahrukh.movieapp.ui.theme.AppPrimaryColor
import com.shahrukh.movieapp.ui.theme.ButtonColor
import com.shahrukh.movieapp.utils.Constants.BASE_BACKDROP_IMAGE_URL
import com.shahrukh.movieapp.utils.Constants.BASE_POSTER_IMAGE_URL
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.viewmodel.DetailsViewModel
import com.shahrukh.movieapp.viewmodel.HomeViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import java.text.SimpleDateFormat
import java.util.*

@Destination
@Composable
fun MovieDetails(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    currentFilm: Film,
    selectedFilmType: FilmType
) {
    var film by remember {
        mutableStateOf(currentFilm)
    }
    val filmType: FilmType = remember { selectedFilmType }

    val date = SimpleDateFormat.getDateTimeInstance().format(Date())



    val filmCastList = detailsViewModel.filmCast.value

    LaunchedEffect(key1 = film) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33F)
        ) {
            val (
                backdropImage,
                backButton,
                movieTitleBox,
                moviePosterImage,
                translucentBr
            ) = createRefs()

            CoilImage(
                imageModel = "$BASE_BACKDROP_IMAGE_URL${film.backdropPath}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .fillMaxHeight()
                    .constrainAs(backdropImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                failure = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                    ) {
                        //Image(
                          //  painter = painterResource(id = ),
                            //contentDescription = "no image"
                        //)
                    }
                },
                shimmerParams = ShimmerParams(
                    baseColor = AppPrimaryColor,
                    highlightColor = ButtonColor,
                    durationMillis = 500,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                contentScale = Crop,
                contentDescription = "Header backdrop image",
            )

            BackButton(modifier = Modifier
                .constrainAs(backButton) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }) {
                navigator.navigateUp()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color(0XFF180E36).copy(alpha = 0.5F),
                                Color(0XFF180E36)
                            ),
                            startY = 0.1F
                        )
                    )
                    .constrainAs(translucentBr) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(backdropImage.bottom)
                    }
            )

            Column(
                modifier = Modifier.constrainAs(movieTitleBox) {
                    start.linkTo(moviePosterImage.end, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    bottom.linkTo(moviePosterImage.bottom, margin = 10.dp)
                },
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = when (filmType) {
                            FilmType.TVSHOW -> "Series"
                            FilmType.MOVIE -> "Movie"
                        },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(size = 4.dp))
                            .background(Color.DarkGray.copy(alpha = 0.65F))
                            .padding(2.dp),
                        color = AppOnPrimaryColor.copy(alpha = 0.78F),
                        fontSize = 12.sp,
                    )
                    Text(
                        text = when (film.adult) {
                            true -> "18+"
                            else -> "PG"
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(shape = RoundedCornerShape(size = 4.dp))
                            .background(
                                if (film.adult) Color(0xFFFF7070) else Color.DarkGray.copy(
                                    alpha = 0.65F
                                )
                            )
                            .padding(2.dp),
                        color = AppOnPrimaryColor.copy(alpha = 0.78F),
                        fontSize = 12.sp,
                    )
                }

                Text(
                    text = film.title,
                    modifier = Modifier
                        .padding(top = 2.dp, start = 4.dp, bottom = 4.dp)
                        .fillMaxWidth(0.5F),
                    maxLines = 2,
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    color = Color.White.copy(alpha = 0.78F)
                )

                Text(
                    text = film.releaseDate,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
                    fontSize = 15.sp,
                    fontWeight = Light,
                    color = Color.White.copy(alpha = 0.56F)
                )




            }

            CoilImage(
                imageModel = "$BASE_POSTER_IMAGE_URL/${film.posterPath}",
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .width(115.dp)
                    .height(172.5.dp)
                    .constrainAs(moviePosterImage) {
                        top.linkTo(backdropImage.bottom)
                        bottom.linkTo(backdropImage.bottom)
                        start.linkTo(parent.start)
                    }, failure = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                       // Image(
                           // painter = painterResource(id = R.drawable.image_not_available),
                           // contentDescription = "no image"
                        //)
                    }
                },
                shimmerParams = ShimmerParams(
                    baseColor = AppPrimaryColor,
                    highlightColor = ButtonColor,
                    durationMillis = 500,
                    dropOff = 0.65F,
                    tilt = 20F
                ),

                contentScale = Crop,
                circularReveal = CircularReveal(duration = 1000),
                contentDescription = "movie poster"
            )
        }

        LazyRow(
            modifier = Modifier
                .padding(top = (96).dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        ) {
            val filmGenres: List<Genre> = homeViewModel.filmGenres.filter { genre ->
                return@filter if (film.genreIds.isNullOrEmpty()) false else
                    film.genreIds!!.contains(genre.id)
            }
            filmGenres.forEach { genre ->
                item {
                    MovieGenreChip(
                        background = ButtonColor,
                        textColor = AppOnPrimaryColor,
                        genre = genre.name
                    )
                }
            }
        }

        ExpandableText(
            text = film.overview,
            modifier = Modifier
                .padding(top = 3.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        )

        LazyColumn(
            horizontalAlignment = Alignment.Start
        ) {
            item {
                AnimatedVisibility(visible = (filmCastList.isNotEmpty())) {
                    Text(
                        text = "Cast",
                        fontWeight = Bold,
                        fontSize = 18.sp,
                        color = AppOnPrimaryColor,
                        modifier = Modifier.padding(start = 4.dp, top = 6.dp, bottom = 4.dp)
                    )
                }
            }
            item {
                LazyRow(modifier = Modifier.padding(4.dp)) {
                    filmCastList.forEach { cast ->
                        item { CastMember(cast = cast) }
                    }
                }
            }



        }
    }
}

@Composable
fun CastMember(cast: Cast?) {
    Column(
        modifier = Modifier.padding(end = 8.dp, top = 2.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp),
            imageModel = "$BASE_POSTER_IMAGE_URL/${cast!!.profilePath}",
            shimmerParams = ShimmerParams(
                baseColor = AppPrimaryColor,
                highlightColor = ButtonColor,
                durationMillis = 500,
                dropOff = 0.65F,
                tilt = 20F
            ),
            failure = {

            },

            contentScale = Crop,
            circularReveal = CircularReveal(duration = 1000),
            contentDescription = "cast image"
        )
        Text(
            text = trimName(cast.name),
            maxLines = 1,
            color = AppOnPrimaryColor.copy(alpha = 0.5F),
            fontSize = 14.sp,
        )
        Text(
            text = trimName(cast.department),
            maxLines = 1,
            color = AppOnPrimaryColor.copy(alpha = 0.45F),
            fontSize = 12.sp,
        )
    }
}

fun trimName(name: String): String {
    return if (name.length <= 10) name else {
        name.removeRange(8..name.lastIndex) + "..."
    }
}