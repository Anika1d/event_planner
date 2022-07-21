@file:OptIn(ExperimentalAnimationApi::class)

package com.template.eventplanner.composenavigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.template.eventplanner.composescreens.mainScreen
import com.template.eventplanner.fragments.viewmodels.MainViewModel

@ExperimentalMaterial3Api
@Composable
fun NavigationModule(
    navControllerCompose: NavHostController,
    navController: NavController,
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel
) {
    AnimatedNavHost(
        navController = navControllerCompose,
        startDestination = Route.AllCurrentEventsScreen.route
    ) {
        composable(route = Route.AllCurrentEventsScreen.route) {
            mainScreen(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(1f),
                navControllerCompose = navControllerCompose,
                navController = navController,
                lifecycle = lifecycleOwner,
                viewModel = mainViewModel,
            )
        }
    }
}


sealed class Route(val route: String) {
    object LikedEventsScreen : Route("liked_events_screen")
    object VisitedEventsScreen : Route("visited_events_screen")
    object IsNotVisitedEventsScreen : Route("is_not_visited_events_screen")
    object AllCurrentEventsScreen : Route("all_current_events_screen")
}