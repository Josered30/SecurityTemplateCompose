package com.example.securitytemplate.ui.modules


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.securitytemplate.core.auth.AuthState
import com.example.securitytemplate.core.navigation.*
import com.example.securitytemplate.ui.viewModels.HomeViewModel
import com.example.securitytemplate.ui.viewModels.LoginViewModel
import com.example.securitytemplate.ui.modules.auth.LoginPage
import com.example.securitytemplate.ui.viewModels.MainViewModel
import com.example.securitytemplate.ui.modules.shared.components.BottomBar
import com.example.securitytemplate.ui.modules.home.HomePage
import com.example.securitytemplate.ui.modules.shared.components.Empty
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.*


//@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun MainPage() {

    val mainViewModel: MainViewModel = viewModel()
    val navigationManager = mainViewModel.navigationManager
    val authManager = mainViewModel.authManager

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var init by rememberSaveable {
        mutableStateOf(false)
    }
    var startDestination by rememberSaveable {
        mutableStateOf(HomeDirections.root.destination)
    }

    LaunchedEffect(Unit) {
        val authRoute: (AuthState) -> Unit = {
            startDestination = when (it) {
                AuthState.EMPTY -> AuthDirections.root.destination
                AuthState.AUTH -> HomeDirections.root.destination
                AuthState.UN_AUTH -> AuthDirections.root.destination
            }
            val start = navController.graph.findStartDestination()
            navController.popBackStack(start.id, true)
            navController.graph.setStartDestination(startDestination)
            navController.navigate(startDestination)
        }

        if (!init) {
            if (authManager.checkToken() && authManager.currentValue != AuthState.AUTH) {
                authManager.changeState(AuthState.AUTH)
            } else if (!authManager.checkAuth()) {
                authManager.changeState(AuthState.UN_AUTH)
                authRoute(AuthState.UN_AUTH)
            }
            init = true
        }

        navigationManager.appSharedFlow.onEach {
            navController.navigate(it.destination) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                if (HomeDirections.isMainRoute(it.destination)) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        //saveState = true
                    }
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item¶
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }.launchIn(this)

        authManager.stateSharedFlow.onEach { state ->
            Log.i("AUTH", state.name)
            authRoute(state)
        }.launchIn(this)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            navigationManager.canPop = controller.previousBackStackEntry != null
            navigationManager.currentRoute = destination.route.orEmpty()
        }
    }

    AppNavigation(navController, startDestination, mainViewModel.authManager::checkAuth)
}


@ExperimentalMaterialApi
//@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    checkAuth: () -> Boolean
) {
    NavHost(
        navController,
        startDestination,
    ) {

        navigation(
            startDestination = AuthDirections.default.destination,
            route = AuthDirections.root.destination,
        ) {
            composable(AuthDirections.login.destination) {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginPage(loginViewModel)
            }
        }

        navigation(
            startDestination = HomeDirections.default.destination,
            route = HomeDirections.root.destination,
        ) {
            composable(HomeDirections.home.destination) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomePage(homeViewModel)
            }
        }

    }
}