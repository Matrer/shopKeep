package com.example.shopkeep.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkeep.BardercodeActivity;
import com.example.shopkeep.MakeBarcodeActivity;
import com.example.shopkeep.R;


public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    private ImageView imgslide;


    // list of images
    public int[] lst_images = {
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.shop,
    };
    // list of titles
    public String[] lst_title = {
            "Skanuj! ",
            "Wpisz ręcznie!",
            "Dzienny koszyk",

    }   ;
    // list of descriptions
    public String[] lst_description = {
            "Zeskanuj produkt aby dodać go do osobistego koszyka internetowego",
            "Wpisz kod kreskowy ręcznie jeżeli skanowanie jest niemożliwe",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
    };
    // list of background colors
    public int[]  lst_backgroundcolor = {
            Color.rgb(55,55,55),
            Color.rgb(55,55,55),
            Color.rgb(55,55,55),

    };


    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slideAdapter);
        imgslide = (ImageView)  view.findViewById(R.id.slideimage);
        TextView txttitle= (TextView) view.findViewById(R.id.titleOpisIcon);
        TextView description = (TextView) view.findViewById(R.id.opisTitleIcon);
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        imgslide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0) {
                    Toast.makeText(context,"clicked1",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, BardercodeActivity.class);
                    context.startActivity(intent);
                }

                if(position==1){
                    Intent intent = new Intent(context, MakeBarcodeActivity.class);
                    context.startActivity(intent);
                }
                if(position==2) Toast.makeText(context,"clicked3",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
