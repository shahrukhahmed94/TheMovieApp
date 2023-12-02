package com.shahrukh.movieapp.screens

import android.media.Image
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.screens.destinations.MovieDetailsDestination
import com.shahrukh.movieapp.screens.destinations.SearchScreenDestination
import com.shahrukh.movieapp.sharedComposables.LoopReverseLottieLoader
import com.shahrukh.movieapp.sharedComposables.SearchBar
import com.shahrukh.movieapp.ui.theme.AppOnPrimaryColor
import com.shahrukh.movieapp.ui.theme.AppPrimaryColor
import com.shahrukh.movieapp.ui.theme.ButtonColor
import com.shahrukh.movieapp.utils.Constants.BASE_BACKDROP_IMAGE_URL
import com.shahrukh.movieapp.utils.Constants.BASE_POSTER_IMAGE_URL
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.viewmodel.HomeViewModel
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import retrofit2.HttpException
import java.io.IOException
import com.shahrukh.movieapp.R
import com.shahrukh.movieapp.screens.destinations.ProfileDestination


@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator?,
    homeViewModel: HomeViewModel = hiltViewModel(),
   // watchListViewModel: WatchListViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))

    ) {


        val popularFilms = homeViewModel.popularFilmsState.value.collectAsLazyPagingItems()

        navigator?.let { ProfileAndSearchBar(navigator = it, homeViewModel = homeViewModel ) }








       /** Row {

            Text(text = "The Movie App", color= Color.White, modifier = Modifier.padding(start = 10.dp, top = 15.dp))
            Spacer(modifier = Modifier.weight(1f))

            IconButton(


                onClick = {
                    navigator?.navigate(
                        direction = SearchScreenDestination()
                    ) {
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 5.dp, end = 5.dp),
                    painter = painterResource(id = android.R.drawable.ic_menu_search),
                    contentDescription = "search icon",
                    tint = AppOnPrimaryColor
                )
            }
        }*/


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),


            ) {
            items(popularFilms.itemCount)
            { index ->
                popularFilms[index]?.let {
                    // FeedItem(it)

                    print(it.title)


                    Column(modifier = Modifier
                        .size(160.dp)
                        .clickable {


                            navigator?.navigate(
                                direction = MovieDetailsDestination(
                                    it,
                                    selectedFilmType = FilmType.MOVIE
                                )
                            ) {
                                launchSingleTop = true
                            }


                        }) {

                        com.skydoves.landscapist.coil.CoilImage(
                            modifier = Modifier
                                .height(120.dp)
                                .padding(top = 10.dp),
                            imageModel = BASE_POSTER_IMAGE_URL + it.posterPath,
                            shimmerParams = ShimmerParams(
                                baseColor = AppPrimaryColor,
                                highlightColor = ButtonColor,
                                durationMillis = 500,
                                dropOff = 0.65F,
                                tilt = 20F
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = it.title,
                            color = Color.White,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }


                }
            }
        }


    }


}



@Composable
fun ProfileAndSearchBar(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel
) {
    Row(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            contentAlignment = Center
        ) {
            Box(
                modifier = Modifier
                    .size(53.dp)
                    .clip(CircleShape)
                // .background(AppOnPrimaryColor)
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(AppPrimaryColor)
            )
            IconButton(onClick = {
                navigator.navigate(
                    direction = ProfileDestination()
                ) {
                    launchSingleTop = true
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_profile),
                    tint = AppOnPrimaryColor,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "profile picture"
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val filmTypes = listOf(FilmType.MOVIE, FilmType.TVSHOW)
            val selectedFilmType = homeViewModel.selectedFilmType.value

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                filmTypes.forEachIndexed { index, filmType ->
                    Text(
                        text = if (filmType == FilmType.MOVIE) "Movies" else "Tv Shows",
                        fontWeight = if (selectedFilmType == filmTypes[index]) FontWeight.Bold else Light,
                        fontSize = if (selectedFilmType == filmTypes[index]) 24.sp else 16.sp,
                        color = if (selectedFilmType == filmTypes[index])
                            AppOnPrimaryColor else Color.LightGray.copy(alpha = 0.78F),
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (homeViewModel.selectedFilmType.value != filmTypes[index]) {
                                    homeViewModel.selectedFilmType.value = filmTypes[index]
                                    homeViewModel.getFilmGenre()
                                    homeViewModel.refreshAll(null)
                                }
                            }
                    )
                }
            }

            val animOffset = animateDpAsState(
                targetValue = when (filmTypes.indexOf(selectedFilmType)) {
                    0 -> (-35).dp
                    else -> 30.dp
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy
                )
            )

            Box(
                modifier = Modifier
                    .width(46.dp)
                    .height(2.dp)
                    .offset(x = animOffset.value)
                    .clip(RoundedCornerShape(4.dp))
                    .background(AppOnPrimaryColor)
            )
        }

        IconButton(
            onClick = {
                navigator.navigate(
                    direction = SearchScreenDestination()
                ) {
                    launchSingleTop = true
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search icon",
                tint = AppOnPrimaryColor
            )
        }
    }
}



