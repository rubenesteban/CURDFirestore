package com.travel.curdfirestore.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.travel.curdfirestore.util.SharedViewModel

//import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun TimerScreen(navController : NavController, sharedViewModel: SharedViewModel ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        sharedViewModel.apply {
            Text(text = timerText.value, fontSize = 28.sp)
            Button(onClick = {
                if (isPlaying.value) stopCountDownTimer() else startCountDownTimer()
            }) {
                Text(text = if (isPlaying.value) "Stop CountDown" else "Start CountDown")
            }
            Button(onClick = {
                resetCountDownTimer()
            }) {
                Text(text = "Reset Timer")
            }
        }
    }
}