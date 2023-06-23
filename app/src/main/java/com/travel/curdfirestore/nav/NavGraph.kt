package com.travel.curdfirestore.nav


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.travel.curdfirestore.screen.*
import com.travel.curdfirestore.util.SharedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val uiState by sharedViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var pression: Boolean by remember { mutableStateOf(false) }
    var profession: String by remember { mutableStateOf("") }

    fun yup(): String {
        if (true){
            profession ="TimerScreen"
        }else{
            profession = "Phf"
        }

        return profession

    }


    suspend fun loadWeather() {
        yup()
    }

    LaunchedEffect(Unit) {

        loadWeather()
    }
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        // main screen
        composable(
            route = Screens.MainScreen.route
        ) {
            MainScreen(
                navController = navController,
            )
        }
        composable(
            route = Screens.LoginScreen.route
        ) {
            LoginScreen(
                navController = navController,
                viewModel = LoginViewModel()
            )
        }

        // get data screen
        composable(
            route = Screens.GetDataScreen.route
        ) {
            GetDataScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }

        composable(
            route = Screens.Pdf.route
        ) {
            Pdf(
                navController = navController,
                sharedViewModel = sharedViewModel,
                openpluma1 = {   navController.navigate(route = Screens.Pdf.route) },
                openpluma2 = {   navController.navigate(route = Screens.Phf.route) },
                openpluma3 = {   navController.navigate(route = Screens.Parkin.route) },
                openpluma4 =  uiState.name,
                openpluma5 =   uiState.tel
            )
        }

        composable(
            route = Screens.Phf.route
        ) {
            Phf(
                navController = navController,
                sharedViewModel = sharedViewModel,
                viewModel = LoginViewModel(),
                openpluma1 = {   navController.navigate(route = Screens.Pdf.route) },
                openpluma2 = {   navController.navigate(route = Screens.Phf.route) },
                openpluma3 = {   navController.navigate(route = Screens.TimerScreen.route) }

            )
        }


        composable(
            route = Screens.TimerScreen.route
        ) {
           TimerScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
                )
        }



        composable(
            route = Screens.Parkin.route
        ) {
         Parkin(
                navController = navController,
                sharedViewModel = sharedViewModel

            )
        }

        // add data screen
        composable(
            route = Screens.AddDataScreen.route
        ) {
            AddDataScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
    }
}