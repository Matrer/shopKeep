package com.example.shopkeep;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class menuActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        viewPager = (ViewPager) findViewById(R.id.vievpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);
    }
}
