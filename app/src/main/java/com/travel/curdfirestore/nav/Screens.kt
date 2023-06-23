package com.travel.curdfirestore.nav

sealed class Screens(val route: String) {
    object MainScreen: Screens(route = "main_screen")
    object LoginScreen: Screens(route = "login_screen")
    object GetDataScreen: Screens(route = "get_data_screen")
    object AddDataScreen: Screens(route = "add_data_screen")
    object TimerScreen: Screens(route = "time_screen")
    object Pdf: Screens(route = "add_pdf_screen")
    object Phf: Screens(route = "add_phf_screen")
    object Parkin: Screens(route = "add_park_screen")
}
