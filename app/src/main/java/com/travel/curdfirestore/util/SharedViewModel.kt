package com.travel.curdfirestore.util

import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.travel.curdfirestore.R
import com.travel.curdfirestore.screen.OrderUiState
import com.travel.curdfirestore.screen.TimeFormatExt.timeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SharedViewModel() : ViewModel() {


    val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()





    fun saveData(
        userData: UserData,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {

        if(userData.userID.isEmpty() || userData.name.isEmpty() ||  userData.profession.isEmpty()  || userData.age.toString().isEmpty() || uiState.value.lati.toString().isEmpty() || uiState.value.long.toString().isEmpty()) {
            _uiState.update {
                it.copy(R.string.empty.toDouble())
            }
            return@launch
        }

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userData.userID)

        try {
            fireStoreRef.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun retrieveData(
        userID: String,
        context: Context,
        data: (UserData) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    // for getting single or particular document
                    if (it.exists()) {
                        val userData = it.toObject<UserData>()!!
                        data(userData)
                    } else {
                        Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(
        userID: String,
        context: Context,
        navController: NavController,
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully deleted data", Toast.LENGTH_SHORT)
                        .show()
                    navController.popBackStack()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun Datey(UseGuess: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                lati = UseGuess
            )
        }

    }

    fun Ditey(UseGuess: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                long = UseGuess
            )
        }

    }

    fun Dotey(UseGuess: String) {
        _uiState.update { currentState ->
            currentState.copy(name= UseGuess
            )
        }

    }

    fun Sotey(UseGuess: String) {
        _uiState.update { currentState ->
            currentState.copy(userID = UseGuess
            )
        }

    }



    fun Rotey(UseGuess: String,a:String, s:String, f:Int) {
        _uiState.update { currentState ->
            currentState.copy(userID = UseGuess,
            name = a, profession = s, age = 0
            )
        }

    }






    fun Gotey(UseGuess: String) {
        _uiState.update { currentState ->
            currentState.copy(profession = UseGuess
            )
        }

    }


    fun yotey(UseGuess: Int) {
        _uiState.update { currentState ->
            currentState.copy(age = UseGuess
            )
        }

    }

    fun name(): String {
        var eco = uiState.value.name
        return eco
    }

    fun analy(): Double {
        var eco = uiState.value.lati
        return eco
    }



    fun analysis(): Double {
        var eco = uiState.value.long
        return eco
    }



    fun tel(): String {
        var eco = uiState.value.tel
        return eco
    }

    fun Detey(UseGuess: String) {
        _uiState.update { currentState ->
            currentState.copy(
                tel = UseGuess
            )
        }
    }


        fun fetey(UseGuess: Boolean) {
            _uiState.update { currentState ->
                currentState.copy(
                    save = UseGuess
                )
            }

        }


        //-------------

        private var countDownTimer: CountDownTimer? = null

        private val userInputHour = TimeUnit.HOURS.toMillis(23)
        private val userInputMinute = TimeUnit.MINUTES.toMillis(59)
        private val userInputSecond = TimeUnit.SECONDS.toMillis(59)

        val initialTotalTimeInMillis = userInputHour + userInputMinute + userInputSecond
        var timeLeft = mutableStateOf(initialTotalTimeInMillis)
        val countDownInterval = 1000L // 1 seconds is the lowest

        val timerText = mutableStateOf(timeLeft.value.timeFormat())

        val isPlaying = mutableStateOf(false)

        fun startCountDownTimer() = viewModelScope.launch {
            isPlaying.value = true
            countDownTimer = object : CountDownTimer(timeLeft.value, countDownInterval) {
                override fun onTick(currentTimeLeft: Long) {
                    timerText.value = currentTimeLeft.timeFormat()
                    timeLeft.value = currentTimeLeft
                }

                override fun onFinish() {
                    timerText.value = initialTotalTimeInMillis.timeFormat()
                    isPlaying.value = false
                }
            }.start()
        }

        fun stopCountDownTimer() = viewModelScope.launch {
            isPlaying.value = false
            countDownTimer?.cancel()
        }

        fun resetCountDownTimer() = viewModelScope.launch {
            isPlaying.value = false
            countDownTimer?.cancel()
            timerText.value = initialTotalTimeInMillis.timeFormat()
            timeLeft.value = initialTotalTimeInMillis
        }


    }

