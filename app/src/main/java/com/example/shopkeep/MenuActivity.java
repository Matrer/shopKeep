package com.example.shopkeep;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shopkeep.Adapters.SlideAdapter;

public class MenuActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter myadapter;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        login = getIntent().getStringExtra("login");

        Toast.makeText(this,login,Toast.LENGTH_SHORT).show();
        viewPager = findViewById(R.id.vievpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);
    }
}
