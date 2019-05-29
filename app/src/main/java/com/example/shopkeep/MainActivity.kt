package com.example.shopkeep


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shopkeep.Static.Shared
import com.google.android.gms.flags.impl.SharedPreferencesFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonRegisterShop.setOnClickListener {
            Toast.makeText(applicationContext, "Rejestracja", Toast.LENGTH_LONG).show()
            openRegistrationActivity()
        }
        TextLogin.setOnClickListener { loginFunction() }

        Toast.makeText(this,Shared.getUserName(applicationContext),Toast.LENGTH_SHORT).show()
        editTextLogin.setText(Shared.getUserName(applicationContext))
        editTextPassword.setText(Shared.getPassword(applicationContext))
    }


    private fun openRegistrationActivity() {
        val intent = Intent(applicationContext, RegistrationClass::class.java)
        startActivity(intent)
    }

    private fun loginFunction() {

        val pass = editTextPassword.text.toString()
        val log = editTextLogin.text.toString()

        if (pass.isNotEmpty() || log.isNotEmpty()) {
            login(log, pass)
        } else {
            editTextPassword.error = "Brak hasła!"
            TextLogin.error = "Brak loginu!"

        }

    }

    private fun login(login: String, password: String) {


        val stringRequest = object : StringRequest(Method.POST, URL, Response.Listener
        { response ->

            val responseTrimed = response.substringBefore("<!-- End //]]>")

            val jsonObject = JSONObject(responseTrimed)
            val success = jsonObject.getString("success")
            val jsonArray = jsonObject.getJSONArray("logins")

            if (success == "1")
            {
                for (i in 0 until jsonArray.length())
                {

                    //wykurwia apkę jak coś źle odbierze xd
                    val jsonObjectRecived = jsonArray.getJSONObject(i)

                    val login = jsonObjectRecived.getString("login").trim()

                    //Przekazanie loginu dalej czyli zalogowanny przyda się podczas dodawania produktu do koszyka
                    //Ale niech narazie tak będzie ;)

                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    intent.putExtra("login", login)

                    Shared.save(login,password,applicationContext)

                    startActivity(intent)

                }
            }
            else
            {
                Toast.makeText(applicationContext, "Zły login lub hasło", Toast.LENGTH_SHORT).show()
            }

        },Response.ErrorListener
        {
            Toast.makeText(applicationContext, "Błąd Połączenia! ${it.message}", Toast.LENGTH_SHORT).show()
            startActivity(intent)
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
        private const val URL = "http://kryptoprojekt.prv.pl/login.php"
    }
}
