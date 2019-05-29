package com.example.shopkeep

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.TextLogin
import kotlinx.android.synthetic.main.activity_regestration_class.*
import org.json.JSONObject
import java.util.*

class RegistrationClass : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regestration_class)

        buttonRegisterShop.setOnClickListener { registration() }
    }


    private fun registration()
    {


        val login = EditTextLogin.text.toString().trim()
        val password = TextPassword.text.toString().trim()
        val password2 = TextPassword2.text.toString().trim()

        if(login.isEmpty() || password.isEmpty() || password2.isEmpty() )
        {
            Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_LONG).show()
            return
        }

        if(password2 != password)
        {
            Toast.makeText(this, "Hasła się nie zgadzają", Toast.LENGTH_LONG).show()
            return
        }



        val stringRequest = object : StringRequest(Method.POST, URL, Response.Listener { response ->
            val responseTrimed = response.substringBefore("<!-- End //]]>")

            Toast.makeText(this, responseTrimed, Toast.LENGTH_LONG).show()

            val jsonObject = JSONObject(responseTrimed)

            val success = jsonObject.getString("success")
            if (success == "1")
            {
                Toast.makeText(this, "Sukces!", Toast.LENGTH_LONG).show()
            }

        }, Response.ErrorListener {
            error -> Toast.makeText(this, "Blad!" + error.message, Toast.LENGTH_LONG).show()
        })
        {
            override fun getParams(): Map<String, String>
            {
                val params = HashMap<String, String>()
                params["login"] = login
                params["password"] = password
                return params
            }
        }


        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)

    }

    companion object {
        private const val URL = "http://kryptoprojekt.prv.pl/register.php"
    }
}