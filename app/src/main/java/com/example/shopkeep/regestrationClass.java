package com.example.shopkeep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class regestrationClass extends AppCompatActivity {

    private Button regestrationButton;
    private Button loginButton;
    private EditText login,password,password2;
    private static String URL = "http://192.168.1.13/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration_class);

        regestrationButton = findViewById(R.id.button8);
        loginButton = findViewById(R.id.button9);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);




        regestrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonActtivity();
                //Tutaj przejscie do loginu

            }
        });
    }


    private void loginButtonActtivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void regist(){

        regestrationButton.setVisibility(View.GONE);
        //regestrationButton.setVisibility(View.VISIBLE);

        final String login = this.login.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String password2 = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if(success.equals("1"))
                            {
                                Toast.makeText(getApplicationContext(),"Sukces!",Toast.LENGTH_LONG);
                            }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Blad!" + e.toString(),Toast.LENGTH_LONG);
                    regestrationButton.setVisibility(View.VISIBLE);
                    System.out.println(e.toString());

                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                Toast.makeText(getApplicationContext(),"Blad!" + error.toString(),Toast.LENGTH_LONG);
                regestrationButton.setVisibility(View.VISIBLE);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password",password);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}