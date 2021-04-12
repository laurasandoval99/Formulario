     package com.hsofiamunoz.practica1_hl

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hsofiamunoz.practica1_hl.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

private const val SPACE = " "
private const val EMPTY = ""

class MainActivity : AppCompatActivity() {

    // Creacion de la variable de newBinding
    private lateinit var mainBinding: ActivityMainBinding
    private var users: MutableList<User> = mutableListOf()

    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)

        //Guardar la fecha
        val dataSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val format = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(format, Locale.US)
            val fechaNacimiento = sdf.format(cal.time).toString()
            mainBinding.fechaTextView.text = fechaNacimiento
        }

        mainBinding.fechaNacimientoButton.setOnClickListener {
            DatePickerDialog(this, dataSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Guardar informacion del boton save_button
        mainBinding.saveButton.setOnClickListener {

            val email = mainBinding.emailInputText.text.toString() //email
            val password = mainBinding.passwordInputText.text.toString() //password
            val rep_password = mainBinding.repPasswordInputText.text.toString()// Repetir contraseña
            val gender = if (mainBinding.femaleRadioButton.isChecked) getString(R.string.female) else getString(R.string.male)

            var hobbies = if (mainBinding.danceCheckBox.isChecked) getString(R.string.dance) else EMPTY
            hobbies = hobbies + SPACE + if (mainBinding.eatCheckBox.isChecked) getString(R.string.eat) else EMPTY
            hobbies = hobbies + SPACE + if (mainBinding.readCheckBox.isChecked) getString(R.string.read) else EMPTY
            hobbies = hobbies + SPACE + if (mainBinding.sportCheckBox.isChecked) getString((R.string.sports)) else EMPTY

            var born_date = mainBinding.fechaTextView.text.toString()

            val born_city = mainBinding.bornCitySpinner.selectedItem.toString()

            val compare_born_city = getString(R.string.initial_parameter)


            if (email.isNotEmpty())
            {
                if (hobbies.isNotBlank() && born_date != EMPTY && born_city != compare_born_city)
                {
                       if (password == rep_password) {
                           if (password == EMPTY)
                               Toast.makeText(this, getString(R.string.password_warning), Toast.LENGTH_SHORT).show()
                           else {
                               saveUSer(email, password, gender, hobbies, born_date, born_city)
                               mainBinding.repPasswordTextInputLayout.error = null
                           }
                        }
                        else
                            Toast.makeText(this, getString(R.string.password_error), Toast.LENGTH_SHORT).show()

                } else
                    Toast.makeText(this, getString(R.string.required_parameter_text), Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, getString(R.string.email_error), Toast.LENGTH_SHORT).show()
            cleanViews()

        }
    }

    private fun saveUSer(email: String, password: String, gender: String, hobbies: String, born_date: String, born_city: String) {
        val newUser = User(email, password, gender, hobbies, born_date, born_city)
        users.add(newUser)
        printUserData()

    }

    private fun printUserData() {
        var i = 0
        var info = ""
        for (user in users)
            info = info + "\n\n"+ user.email + "\n" + user.gender + "\n" + user.hobbies + "\n" + user.born_date + "\n" +  user.born_city
        mainBinding.infoTextView.text = info

    }


    private fun cleanViews() {
        with(mainBinding) {
            emailInputText.setText(EMPTY)
            passwordInputText.setText(EMPTY)
            repPasswordInputText.setText(EMPTY)
            femaleRadioButton.isChecked = true
            danceCheckBox.isChecked = false
            eatCheckBox.isChecked = false
            sportCheckBox.isChecked = false
            readCheckBox.isChecked = false
            fechaTextView.text = EMPTY
            bornCitySpinner.setSelection(0)

        }
    }

}