package com.android.assignment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.assignment.constants.Constants
import com.android.assignment.viewmodel.MovieViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun MovieListScreen(onClick: (title:String, posterImg:String, overView:String) -> Unit) {
    val movieViewModel: MovieViewModel = viewModel()
    val movies by movieViewModel.filteredMovies.collectAsState()
    val searchQuery by movieViewModel.searchQuery.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { movieViewModel.updateSearchQuery(it) },
            placeholder = { Text(text = "Search movies", fontSize = 18.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.LightGray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.LightGray,

                )
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            items(movies) {
                MovieItem(movieName = it.title, modalUrl = it.backdropPath!!,  onClick = {
                    onClick(it.title,it.posterPath,it.overview)
                })
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movieName: String, modalUrl: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable { onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = "${Constants.IMAGE_URL}$modalUrl",
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = movieName,
            style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.Start)
            )
    }
}
