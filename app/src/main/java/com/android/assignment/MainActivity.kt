package com.android.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.assignment.screens.MovieDetailsScreen
import com.android.assignment.screens.MovieListScreen
import com.android.assignment.ui.theme.AndroidEngineerAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidEngineerAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "MovieListScreen") {
        composable("MovieListScreen") {
            MovieListScreen() {
                navController.navigate("MovieDetailsScreen")
            }
        }
        composable("MovieDetailsScreen") {
            MovieDetailsScreen(
                "Dummy",
                "dioqwndqwodq",
                "/ugQkpGajKFQ8eyOEhGheR0HfWQ.jpg"
            ) { navController.popBackStack() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    AndroidEngineerAssignmentTheme {

    }
}