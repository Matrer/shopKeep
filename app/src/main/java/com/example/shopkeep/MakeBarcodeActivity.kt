package com.example.shopkeep

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_make_barcode.*
import java.lang.Exception

class MakeBarcodeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_barcode)
        try {
            val barcode = intent.getStringExtra("barcode")
            editTextCode.setText(barcode)
        }
        catch (ex:Exception){}
    }
}
