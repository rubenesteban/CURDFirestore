package com.travel.curdfirestore.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.travel.curdfirestore.MenuBottomNavigation
import com.travel.curdfirestore.util.SharedViewModel
import com.travel.curdfirestore.util.UserData


@Composable
fun Phf(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    openpluma1: () -> Unit,
    openpluma2: () -> Unit,
    openpluma3: () -> Unit,


    scaffoldState: ScaffoldState = rememberScaffoldState()
) {


    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var profession: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    var ageInt: Int by remember { mutableStateOf(0) }
    var lanInt:Double by remember { mutableStateOf(0.0) }
    var logInt:Double by remember { mutableStateOf(0.0) }

    var latitud: String by remember { mutableStateOf("") }
    var longitud: String by remember { mutableStateOf("") }
    var telefono: String by remember { mutableStateOf("") }

    val context = LocalContext.current



   // val uiState by sharedViewModel.uiState.collectAsState()


    val navigationController = rememberNavController()

    val uiState by viewModel.uiState.collectAsState()

    val dataStore = StoreUserEmail(context)
    val dirStore = StoreUserDireccion(context)

    val savedEmail = dataStore.getEmail.collectAsState(initial = "")
    val savedDirec = dirStore.getDirec.collectAsState(initial = "")

    val telStore = StoreUserTel(context)
    // get saved email
    val savedTel = telStore.getTel.collectAsState(initial = "")

    var phone by remember { mutableStateOf("") }


    suspend fun loadWeather() {
        phone = viewModel.autin()
        latitud = savedEmail.value!!
        longitud = savedDirec.value!!
        sharedViewModel.Datey(latitud.toDouble())
        sharedViewModel.Ditey(longitud.toDouble())
        Log.d("MascotaFeliz", "Creado ${telefono}")

    }

    LaunchedEffect(Unit) {

        loadWeather()
    }

    Scaffold(
        scaffoldState = scaffoldState,

        bottomBar = { MenuBottomNavigation(navController = navigationController, task1 = openpluma1,
            task2 = openpluma2, task3 = openpluma3) },
        modifier = Modifier.fillMaxSize(),
        //Color = MaterialTheme.colors.background
    )
    { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


        ) {


            item {
                Column(
                    modifier = Modifier
                        .padding(start = 60.dp, end = 60.dp, bottom = 50.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // userID
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userID,
                        onValueChange = {
                            userID = it
                        },
                        label = {
                            Text(text = "UserID")
                        }
                    )
                    // Name
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text(text = "Name:")
                        }
                    )
                    // Profession
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = profession,
                        onValueChange = {
                            profession = it
                        },
                        label = {
                            Text(text = "Solicitas o Ofreces: %-*")
                        }
                    )
                    // Age
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = age,
                        onValueChange = {
                            age = it
                            if (age.isNotEmpty()) {
                                ageInt = age.toInt()
                            }
                        },
                        label = {
                            Text(text = "Telefono:")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    // save Button
                    Button(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .fillMaxWidth(),
                        onClick = {
                            val userData = UserData(
                                userID = phone,
                                name = latitud,
                                profession = longitud,
                                age = ageInt
                            )

                            sharedViewModel.saveData(userData = userData, context = context)
                        }
                    ) {
                        Text(text = "Save")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {}
                    ) {
                        Text(text = "Save")
                    }

                }


            }


        }
    }
}

