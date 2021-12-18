package com.example.securitytemplate.ui.modules


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.securitytemplate.core.auth.AuthManager
import com.example.securitytemplate.core.auth.AuthState
import com.example.securitytemplate.core.navigation.*
import com.example.securitytemplate.ui.viewModels.HomeViewModel
import com.example.securitytemplate.ui.viewModels.LoginViewModel
import com.example.securitytemplate.ui.modules.auth.LoginPage
import com.example.securitytemplate.ui.viewModels.MainViewModel
import com.example.securitytemplate.ui.modules.home.HomePage
import com.example.securitytemplate.ui.modules.shared.components.BottomBar
import com.google.accompanist.insets.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.*

import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.composable


@Composable
fun InitNavigation(
    navController: NavHostController,
    navigationManager: BaseNavigationManager,
    authManager: AuthManager,
    setStartDestination: (String) -> Unit
) {
    var init by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {

        val authRoute: (AuthState) -> Unit = {
            val startDestinationAux = when (it) {
                AuthState.AUTH -> {
                    HomeDirections.root.destination
                }
                AuthState.UN_AUTH -> {
                    AuthDirections.root.destination
                }
            }
            setStartDestination(startDestinationAux)
            val start = navController.graph.findStartDestination()
            navController.popBackStack(start.id, true)
            navController.graph.setStartDestination(startDestinationAux)
            navController.navigate(startDestinationAux)
        }

        val navigate: (String) -> Unit = {
            navController.navigate(it) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Restore state when reselecting a previously selected item
                restoreState = true

                // Avoid multiple copies of the same destination when
                // reselecting the same item¶
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                // restoreState = true
            }
        }

        navigationManager.appSharedFlow.onEach {
            navigate(it)
        }.launchIn(this)

        authManager.stateSharedFlow.onEach { state ->
            Log.i("AUTH", state.authState.name)
            authRoute(state.authState)
        }.launchIn(this)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            navigationManager.canPop = controller.previousBackStackEntry != null
            navigationManager.lastRoute = navigationManager.currentRoute
            navigationManager.currentRoute = destination.route.orEmpty()
        }
    }
}

//@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun MainPage() {
    val mainViewModel: MainViewModel = viewModel()
    val navigationManager = mainViewModel.navigationManager
    val authManager = mainViewModel.authManager

    val lifecycleOwner = LocalLifecycleOwner.current

    val showUIFlowLifecycleAware = remember(navigationManager.showUIFlow, lifecycleOwner) {
        navigationManager.showUIFlow.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val imeInsetsFlowLifecycleAware = remember(navigationManager.imeInsetsFlow, lifecycleOwner) {
        navigationManager.imeInsetsFlow.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    var init by rememberSaveable {
        mutableStateOf(false)
    }
    var showUISaveable by rememberSaveable {
        mutableStateOf(false)
    }
    var imeInsetsSaveable by rememberSaveable {
        mutableStateOf(false)
    }
    var startDestination by rememberSaveable {
        mutableStateOf(navigationManager.startDestination.root.destination)
    }

    if (!init) {
        if (authManager.isAuthenticated()) {
            startDestination = HomeDirections.root.destination
            showUISaveable = true
        } else {
            startDestination = AuthDirections.root.destination
            showUISaveable = false
        }
        init = true
    }

    val imeInsets by imeInsetsFlowLifecycleAware.collectAsState(imeInsetsSaveable)
    val showUI by showUIFlowLifecycleAware.collectAsState(showUISaveable)

    val navController = rememberAnimatedNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    showUISaveable = showUI
    imeInsetsSaveable = imeInsets

    val scaffoldState = rememberScaffoldState()

    InitNavigation(
        navController,
        navigationManager,
        authManager,
    ) { startDestination = it }

    val modifier: Modifier = if (imeInsets) {
        Modifier
            .navigationBarsWithImePadding()
    } else {
        Modifier
            .navigationBarsPadding()
    }
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        scaffoldState = scaffoldState,
        bottomBar = {

//            val offset = with(LocalDensity.current) {
//                val bottomPadding =
//                    rememberInsetsPaddingValues(
//                        insets = LocalWindowInsets.current.navigationBars,
//                        applyBottom = true
//                    ).calculateBottomPadding().roundToPx()
//                LocalConfiguration.current.screenHeightDp.dp.roundToPx() + bottomPadding
//            }
//            AnimatedVisibility(
//                visible = showUI,
//                enter = slideInVertically(initialOffsetY = { offset }),
//                exit = slideOutVertically(tween(200), targetOffsetY = { offset })
//            ) {
//                BottomBar(navigationManager, currentRoute.orEmpty())
//            }
            if (showUI) {
                BottomBar(navigationManager, currentRoute.orEmpty())
            }
        },
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            AppNavigation(
                navController,
                startDestination,
                mainViewModel.authManager::isAuthenticated
            )
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
//@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    isAuthenticate: () -> Boolean,
) {

    val screenWidth =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }


    AnimatedNavHost(
        navController,
        startDestination,
    ) {

        navigation(
            startDestination = AuthDirections.default.destination,
            route = AuthDirections.root.destination,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -screenWidth }, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -screenWidth }, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { screenWidth }, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { screenWidth }, animationSpec = tween(700))
            },
        ) {
            composable(
                AuthDirections.login.destination,
                AuthDirections.login.arguments
            ) {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginPage(loginViewModel)
            }
        }

        navigation(
            startDestination = HomeDirections.default.destination,
            route = HomeDirections.root.destination,
            enterTransition = {
                when {
                    AuthDirections.isRoute(initialState.destination.route.orEmpty()) -> slideInHorizontally(
                        initialOffsetX = { screenWidth },
                        animationSpec = tween(700)
                    )
                    else -> null
                }
            },
            exitTransition = {
                when {
                    AuthDirections.isRoute(targetState.destination.route.orEmpty()) -> slideOutHorizontally(
                        targetOffsetX = { screenWidth },
                        animationSpec = tween(700)
                    )
                    else -> null
                }
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -screenWidth },
                    animationSpec = tween(700)
                )

            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -screenWidth },
                    animationSpec = tween(700)
                )

            }
        ) {
            composable(
                HomeDirections.home.destination,
                HomeDirections.home.arguments,
            ) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomePage(homeViewModel)
            }
        }

    }
}