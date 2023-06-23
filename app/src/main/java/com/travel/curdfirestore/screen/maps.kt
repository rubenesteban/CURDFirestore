package com.travel.curdfirestore.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.travel.curdfirestore.util.SharedViewModel




@Composable
fun Parkin(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    //val uiState by sharedViewModel.uiState.collectAsState()
    val llumbising = LatLng(/* latitude = */0.3333333, /* longitude = */ 0.777777)
    val llumbisi = LatLng(/* latitude = */-0.3333333, /* longitude = */ -0.777777)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(llumbising, 30f)
        position = CameraPosition.fromLatLngZoom(llumbisi, 30f)
    }


            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = CameraPositionState()
            ) {
                Marker(
                    state = rememberMarkerState(position = llumbising),
                    draggable = true,
                    title = "Marker",
                    snippet = "My phone is +593-960096928",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                )

                Marker(
                    state = rememberMarkerState(position = llumbisi),
                    draggable = true,
                    title = "Marker",
                    snippet = "My phone is +593-960096928",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )

    }
}



