package com.travel.curdfirestore.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.*
import com.travel.curdfirestore.MenuBottomNavigation
import com.travel.curdfirestore.util.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


data class Ubicacion(  val lati: Double,
                       val long: Double
)



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun Pdf(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier,
    openpluma1: () -> Unit,
    openpluma2: () -> Unit,
    openpluma3: () -> Unit,
    openpluma4: String,
    openpluma5: String,

    scaffoldState: ScaffoldState = rememberScaffoldState()
) {


    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var profession: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    var ageInt: Int by remember { mutableStateOf(0) }
    var lanInt:Double by remember { mutableStateOf(0.0) }
    var logInt:Double by remember { mutableStateOf(0.0) }

    val context = LocalContext.current


    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.INTERNET
        )
    )



    val navigationController = rememberNavController()



    suspend fun loadWeather() {
        name = openpluma4
        profession = openpluma5
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

            item { Sample(multiplePermissionsState) }




            item {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@item
                }
                CurrentLocationContent(
                    gol = { sharedViewModel.Datey(it) },
                    aro = { sharedViewModel.Ditey(it) })
            }
        }


    }
}








@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun CurrentLocationContent(
    gol : (Double) -> Unit,
    aro : (Double) -> Unit

) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var lanInt:Double by remember { mutableStateOf(0.0) }
    var logInt:Double by remember { mutableStateOf(0.0) }
    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }





    val dataStore = StoreUserEmail(context)
    val dirStore = StoreUserDireccion(context)
    //val telStore = StoreUserTel(context)

    val savedEmail = dataStore.getEmail.collectAsState(initial = "")
    val savedDirec = dirStore.getDirec.collectAsState(initial = "")
    // val savedTel = telStore.getTel.collectAsState(initial = "")

    var email by remember { mutableStateOf("") }
    var mil by remember { mutableStateOf("") }

    var locationInfo by remember {
        mutableStateOf("")
    }

    fun gufy(d:Double): Double {
        return d
    }


    suspend fun loadWeather() {
        email = lanInt.toString()
        mil = logInt.toString()



    }

    LaunchedEffect(Unit) {

        loadWeather()
    }
    Column(
        Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                // getting last known location is faster and minimizes battery usage
                // This information may be out of date.
                // Location may be null as previously no client has access location
                // or location turned of in device setting.
                // Please handle for null case as well as additional check can be added before using the method
                scope.launch(Dispatchers.IO) {

                    val result = locationClient.lastLocation.await()
                    email = result.latitude.toString()
                    mil = result.longitude.toString()
                    dataStore.saveEmail(email)
                    dirStore.saveDirec(mil)
                    locationInfo = if (result == null) {
                        "No last known location. Try fetching the current location first"
                    } else {
                        "Current location is \n" + "lat : ${result.latitude}\n" +
                                "long : ${result.longitude}\n" + "fetched at ${System.currentTimeMillis()}"
                    }


                        val priority = if (true) {
                            Priority.PRIORITY_HIGH_ACCURACY
                        } else {
                            Priority.PRIORITY_BALANCED_POWER_ACCURACY
                        }
                        val resulta = locationClient.getCurrentLocation(
                            priority,
                            CancellationTokenSource().token,
                        ).await()
                        resulta?.let { fetchedLocation ->
                            locationInfo =
                                "Current location is \n" + "lat : ${fetchedLocation.latitude}\n" +
                                        "long : ${fetchedLocation.longitude}\n" + "fetched at ${System.currentTimeMillis()}"
                        }
                }
            },
        ) {
            Text("Get last known location")
        }


        Text(
            text = locationInfo,
        )
    }
}




@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Sample(multiplePermissionsState: MultiplePermissionsState) {
    if (multiplePermissionsState.allPermissionsGranted) {
        // If all permissions are granted, then show screen with the feature enabled
        Text(" Ubication permissions Granted! \n Would you like to mover your location\n just drag and drop%\n Thank you! ")
    } else {
        Column {
            Text(
                getTextToShowGivenPermissions(
                    multiplePermissionsState.revokedPermissions,
                    multiplePermissionsState.shouldShowRationale
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                Text("Request permissions")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant all of them for the app to function properly."
        } else {
            " denied. The app cannot function without them."
        }
    )
    return textToShow.toString()
}
