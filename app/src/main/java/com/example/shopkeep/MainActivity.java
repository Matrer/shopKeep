package com.example.shopkeep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText login;
    private EditText password;
    private Button regestrationButton;
    private Button loginButton;
    private static String URL = "http://192.168.1.13/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView2);
        loginButton = findViewById(R.id.login);
        regestrationButton = findViewById(R.id.regestration);
        imageView.setImageResource(R.drawable.shop);


        login = findViewById(R.id.editText12);
        password = findViewById(R.id.editText11);



        regestrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Rejestracja",Toast.LENGTH_LONG);

                openRegestrationActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunction();


            }
        });


    }


    private void openRegestrationActivity(){
        Intent intent = new Intent(getApplicationContext(),regestrationClass.class);
        startActivity(intent);

    }

    private void loginFunction(){

        String pass = password.getText().toString();
        String log = login.getText().toString();
        if(!pass.isEmpty() || !log.isEmpty()){
            loginButton.setVisibility(View.GONE);
            login(log,pass);
        }
        else{
                password.setError("Brak hasła!");
                login.setError("Brak loginu!");

        }

    }

    private void login(final String login,final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("logins");
                    if(success.equals("1"))
                    {
                       for(int i=0;i<jsonArray.length();i++){
                           JSONObject object = jsonArray.getJSONObject(i);

                           String name = object.getString("login").trim();
                           Toast.makeText(getApplicationContext(),name.toString(),Toast.LENGTH_SHORT);
                           //Przekazanie loginu dalej czyli zalogowanny przyda się podczas dodawania produktu do koszyka
                           //Ale niech narazie tak będzie ;)
                           System.out.println(name);
                           Intent intent = new Intent(getApplicationContext(),menuActivity.class);
                           startActivity(intent);

                       }
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Blad!" + e.toString(),Toast.LENGTH_SHORT);
                    regestrationButton.setVisibility(View.VISIBLE);
                    System.out.println(e.toString());

                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                Toast.makeText(getApplicationContext(),"Blad!" + error.toString(),Toast.LENGTH_SHORT);
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
