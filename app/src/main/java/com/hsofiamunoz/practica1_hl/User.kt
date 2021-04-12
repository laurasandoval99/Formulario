package com.hsofiamunoz.practica1_hl

import android.app.DatePickerDialog

data class User (
    var email: String? = null,
    var password: String? = null,
    var gender: String? = null,
    var hobbies : String? = null,
    var born_date : String? = "",
    var born_city : String? = null
)