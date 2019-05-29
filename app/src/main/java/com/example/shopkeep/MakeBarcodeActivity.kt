package com.example.shopkeep

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_make_barcode.*
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

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

            val jsonObject = JSONObject(responseTrimed)
            val success = jsonObject.getString("success")

            if (success == "1")
            {
                Toast.makeText(this,"Dodano",Toast.LENGTH_SHORT).show()
            }
            else
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
                val params = HashMap<String, String>()
                params["login"] = login
                params["code"] = code
                params["name"] = name
                params["price"] = price
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

