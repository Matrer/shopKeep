package com.example.shopkeep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shopkeep.Static.Shared
import com.io.wiemcozjem.Crypt.Java_Cipher
import kotlinx.android.synthetic.main.activity_make_barcode.*
import org.json.JSONObject
import java.util.*

class MakeBarcodeActivity : AppCompatActivity() {

    val login = "Matrer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_barcode)
        try {
            val barcode = intent.getStringExtra("barcode")
            editTextCode.setText(barcode)
        }
        catch (ex:Exception){}


        buttonAdd.setOnClickListener{
            val code = editTextCode.text.toString()
            val name = editTextName.text.toString()
            val price = editTextPrice.text.toString()

            sendBarcode(code,name,price)
        }
    }

    fun sendBarcode(code:String, name:String, price:String)
    {
        if(code.length != 13)
        {
            Toast.makeText(this,"Niepoprawny kod kreskowy masz ${code.length} a powinno być 13",Toast.LENGTH_SHORT).show()
            return
        }
        if(name.isEmpty())
        {
            return
        }

        val stringRequest = object : StringRequest(Method.POST, URL, Response.Listener
        { response ->

            val responseTrimed = response.substringBefore("<!-- End //]]>")
            Toast.makeText(applicationContext, "$responseTrimed", Toast.LENGTH_SHORT).show()

            val jsonObject = JSONObject(responseTrimed)
            val success = jsonObject.getString("success")

            if (success == "1")
            {
                Toast.makeText(this,"Dodano",Toast.LENGTH_SHORT).show()
            }
            else if(success == "0")
            {
                Toast.makeText(this,"Wystąpił problem",Toast.LENGTH_SHORT).show()
            }



        }, Response.ErrorListener
        {
            Toast.makeText(applicationContext, "Błąd Połączenia! ${it.message}", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        })
        {
            override fun getParams(): Map<String, String>
            {
                val password = Shared.getPassword(applicationContext)
                val params = HashMap<String, String>()
                params["login"] = login
                params["code"] = Java_Cipher.encrypt(password,code)
                params["name"] = Java_Cipher.encrypt(name,code)
                params["price"] = Java_Cipher.encrypt(price,code)
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)

    }
    companion object {
        private const val URL = "http://192.168.56.1/krypto/addCode.php"
    }
}

