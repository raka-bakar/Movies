package com.raka.movies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.raka.movies.R
import com.raka.movies.ui.navigation.MainNavigation
import com.raka.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.background)
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        route = MainNavigation.Home.graphId,
                        startDestination = MainNavigation.Home.route
                    ) {
                        MainNavigation.getAllNavigation().forEach {
                            it.compose(navController)
                        }
                    }
                }
            }
        }
    }
}